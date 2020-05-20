import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateNewDB {
	private Connection c;
	private String username;
	private String password;

//Tables in the database
	private String colorQuery = "CREATE TABLE [Colors]([ColorName] [varchar](40) NOT NULL,"
			+ " CONSTRAINT [PK_Colors] PRIMARY KEY CLUSTERED ([ColorName] ASC"
			+ ")WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]"
			+ ") ON [PRIMARY] GO";

	private String hasPieceQuery = "CREATE TABLE  [HasPiece](	[PieceNumber] [varchar](20) NULL,"
			+ "	[SetNumber] [int] NULL,	[Color] [varchar](40) NULL,	[Amount] [int] NULL"
			+ ") ON [PRIMARY] GO"
			+ "ALTER TABLE  [HasPiece]  WITH CHECK ADD  CONSTRAINT [FK__HasPiece__Color__300424B4] FOREIGN KEY([Color])"
			+ "REFERENCES  [Colors] ([ColorName]) GO"
			+ "ALTER TABLE  [HasPiece] CHECK CONSTRAINT [FK__HasPiece__Color__300424B4] GO"
			+ "ALTER TABLE  [HasPiece]  WITH CHECK ADD  CONSTRAINT [FK__HasPiece__PieceN__2E1BDC42] FOREIGN KEY([PieceNumber])"
			+ "REFERENCES  [Pieces] ([PartNumber]) GO"
			+ "ALTER TABLE  [HasPiece] CHECK CONSTRAINT [FK__HasPiece__PieceN__2E1BDC42] GO"
			+ "ALTER TABLE  [HasPiece]  WITH CHECK ADD  CONSTRAINT [FK__HasPiece__SetNum__2F10007B] FOREIGN KEY([SetNumber])"
			+ "REFERENCES  [LEGO_Sets] ([SetNumber]) GO"
			+ "ALTER TABLE  [HasPiece] CHECK CONSTRAINT [FK__HasPiece__SetNum__2F10007B] GO";

	private String setQuery = "CREATE TABLE  [LEGO_Sets](	[SetNumber] [int] NOT NULL,"
			+ "	[MinAge] [int] NOT NULL,	[MaxAge] [int] NULL,	[SetName] [varchar](40) NOT NULL,"
			+ "	[Cost] [money] NOT NULL,	[Theme] [int] NULL,PRIMARY KEY CLUSTERED ("
			+ "	[SetNumber] ASC"
			+ ")WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]"
			+ ") ON [PRIMARY]GOALTER TABLE  [LEGO_Sets]  WITH CHECK ADD FOREIGN KEY([Theme])"
			+ "REFERENCES  [Themes] ([ThemeID])  Go";

	private String ownsPieceQuery = "CREATE TABLE  [OwnsPiece](	[PartNumber] [varchar](20) NULL,"
			+ "	[Username] [varchar](50) NULL,	[Color] [varchar](40) NULL,	[Quantity] [int] NULL"
			+ ") ON [PRIMARY] Go"
			+ "ALTER TABLE  [OwnsPiece]  WITH CHECK ADD  CONSTRAINT [FK__OwnsPiece__Color__34C8D9D1] FOREIGN KEY([Color])"
			+ "REFERENCES  [Colors] ([ColorName]) Go"
			+ "ALTER TABLE  [OwnsPiece] CHECK CONSTRAINT [FK__OwnsPiece__Color__34C8D9D1] Go"
			+ "ALTER TABLE  [OwnsPiece]  WITH CHECK ADD  CONSTRAINT [FK__OwnsPiece__Piece__32E0915F] FOREIGN KEY([PartNumber])"
			+ "REFERENCES  [Pieces] ([PartNumber]) Go"
			+ "ALTER TABLE  [OwnsPiece] CHECK CONSTRAINT [FK__OwnsPiece__Piece__32E0915F] Go"
			+ "ALTER TABLE  [OwnsPiece]  WITH CHECK ADD  CONSTRAINT [FK__OwnsPiece__Usern__33D4B598] FOREIGN KEY([Username])"
			+ "REFERENCES  [Users] ([Username]) Go"
			+ "ALTER TABLE  [OwnsPiece] CHECK CONSTRAINT [FK__OwnsPiece__Usern__33D4B598] Go";

	private String ownsSetQuery = "CREATE TABLE  [OwnsSet](	[SetNumber] [int] NOT NULL,"
			+ "	[Username] [varchar](50) NOT NULL,	[Quantity] [int] NULL,"
			+ " CONSTRAINT [PK__OwnsSet__3E655D51BD295835] PRIMARY KEY CLUSTERED (	[SetNumber] ASC,"
			+ "	[Username] ASC"
			+ ")WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]"
			+ ") ON [PRIMARY] Go"
			+ "ALTER TABLE  [OwnsSet]  WITH CHECK ADD  CONSTRAINT [FK__OwnsSet__SetNumb__619B8048] FOREIGN KEY([SetNumber])"
			+ "REFERENCES  [LEGO_Sets] ([SetNumber]) Go"
			+ "ALTER TABLE  [OwnsSet] CHECK CONSTRAINT [FK__OwnsSet__SetNumb__619B8048] Go"
			+ "ALTER TABLE  [OwnsSet]  WITH CHECK ADD  CONSTRAINT [FK__OwnsSet__Usernam__60A75C0F] FOREIGN KEY([Username])"
			+ "REFERENCES  [Users] ([Username]) Go"
			+ "ALTER TABLE  [OwnsSet] CHECK CONSTRAINT [FK__OwnsSet__Usernam__60A75C0F] Go";

	private String piecesQuery = "CREATE TABLE  [Pieces](	[PartNumber] [varchar](20) NOT NULL,"
			+ "	[Part_Name] [varchar](100) NOT NULL,"
			+ " CONSTRAINT [PK__Pieces__7BB41F31DCB256E5] PRIMARY KEY CLUSTERED (	[PartNumber] ASC"
			+ ")WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]"
			+ ") ON [PRIMARY] Go";

	private String themesQuery = "CREATE TABLE  [Themes](	[Name] [varchar](40) NOT NULL,"
			+ "	[ThemeID] [int] IDENTITY(1,1) NOT NULL,PRIMARY KEY CLUSTERED (	[ThemeID] ASC"
			+ ")WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]"
			+ ") ON [PRIMARY] Go";

	private String usersQuery = "CREATE TABLE  [Users](	[Username] [varchar](50) NOT NULL,"
			+ "	[PasswordHash] [varchar](50) NOT NULL,	[PasswordSalt] [varchar](50) NOT NULL,"
			+ " CONSTRAINT [PK__Users__536C85E59B32A24D] PRIMARY KEY CLUSTERED (	[Username] ASC"
			+ ")WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]"
			+ ") ON [PRIMARY] Go";

	private String wishlistQuery = "CREATE TABLE  [WishListedSets](	[SetNumber] [int] NOT NULL,"
			+ "	[Username] [varchar](50) NOT NULL,PRIMARY KEY CLUSTERED (	[SetNumber] ASC,"
			+ "	[Username] ASC"
			+ ")WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]"
			+ ") ON [PRIMARY] GoALTER TABLE  [WishListedSets]  WITH CHECK ADD FOREIGN KEY([Username])"
			+ "REFERENCES  [Users] ([Username]) Go";

//Views in the database
	private String allOwnedQuery = "CREATE View [AllOwned] AsSELECT [Username]      ,[partNumber]"
			+ "      ,[Color]     ,Sum([Quantity]) as Quantity,	 Part_Name"
			+ "  FROM [Lego_Database].[AllOwned_NoGrouping]  Group By Username, PartNumber, Color, Part_Name"
			+ " Go";

	private String allOwnedQueryNoGrouping = "CREATE View [AllOwned_NoGrouping] as"
			+ "Select Username, pieces.partNumber, Color, Sum(Quantity) As Quantity, Part_NameFrom OwnsPiece"
			+ "Join Pieces on Pieces.PartNumber = OwnsPiece.PartNumber"
			+ "Group By Username, pieces.partNumber, Color, Part_NameUnion All"
			+ "Select OwnsSet.Username, hasPiece.PieceNumber, HasPiece.Color, HasPiece.Amount*OwnsSet.Quantity, Part_Name"
			+ "From OwnsSetJoin HasPiece on ownsset.SetNumber = haspiece.SetNumber"
			+ "Join Pieces on HasPiece.PieceNumber = Pieces.PartNumber Go";

//Stored Procedures in the database
	private String addColorQuery = "Create procedure [addColor]@ColorName varchar(40)As"
			+ "if((Select count(ColorName) from Colors where ColorName =@ColorName)=0)Begin"
			+ "insert into Colors(ColorName)Values(@ColorName)End Go";

	private String addThemeQuery = "CREATE Procedure [AddTheme]@ThemeName varchar(40)AS"
			+ "IF (Select ThemeID from Themes where Name=@ThemeName) is Not NULLBegin"
			+ "Print 'That Theme already exists'Return 1;ENDBeginInsert into Themes (Name)"
			+ "Values (@ThemeName)End Go";

	private String addPieceToCollectionQuery = "CREATE procedure [AddPieceToCollection]@Username varchar(20),"
			+ "@Color varchar(40),@PartNumber varchar(20),@Quantity intAs"
			+ "if (Select Part_Name from pieces where PartNumber=@PartNumber) is nullBegin"
			+ "print 'piece '+@PartNumber+' does not exist'return 1End"
			+ "if (Select Username from Users where Username=@Username) is nullBegin"
			+ "print 'User '+@Username+' does not exist'return 2Endif (@Quantity <= 0)Begin"
			+ "print 'Quantity cannot be nagative'return 3End"
			+ "if (Select ColorName from Colors where LOWER(ColorName)=LOWER(@Color)) is nullBegin"
			+ "print 'color '+@Color+' does not exist'return 4End"
			+ "if (select quantity from ownsPiece where Username=@username and Lower(color) = Lower(@Color) and partNumber = @PartNumber) is not null"
			+ "BeginUpdate OwnsPieceSet Quantity = Quantity+@Quantity"
			+ "Where Username=@username and color = @Color and partNumber = @PartNumberreturn 0End"
			+ "insert into OwnsPiece (Username, color, partnumber, quantity)"
			+ "values (@Username, @Color, @PartNumber, @Quantity) Go";

	private String addPieceToSetQuery = "CREATE Procedure [AddToSet]@PieceNumber varchar(20),@SetNumber int,"
			+ "@count int = 1,@color varchar(20)Asif(select PieceNumberfrom HasPiece"
			+ "where @PieceNumber = PieceNumber and @SetNumber = SetNumber and @color = Color) is not null"
			+ "BeginUpdate HasPieceSet Amount = Amount + @count"
			+ "where PieceNumber = @PieceNumber and @SetNumber = SetNumber and Color = @colorreturn 0End"
			+ "if(select PartNumberfrom Pieceswhere PartNumber=@PieceNumber) is nullBegin"
			+ "Print 'That piece does not exist'return 1Endif(select SetNumberfrom LEGO_Sets"
			+ "where @SetNumber = SetNumber) is nullbeginPrint 'That set does not exist'return 1End"
			+ "if(select ColorNamefrom Colorswhere LOWER(ColorName)=Lower(@color)) is nullBegin"
			+ "Print 'That color does not exist'return 1endif(@count<1)Begin"
			+ "Print 'Cannot have non-positive amount'return 1End"
			+ "insert into HasPiece(SetNumber,PieceNumber,Amount,Color)"
			+ "values (@SetNumber,@PieceNumber,@count,@color) Go";

	private String newPieceQuery = "Create Procedure [NewPiece]@PartNumber varchar(20),@PartName varchar(40)"
			+ "Asif (Select Part_Name From Pieces Where @PartNumber=PartNumber) is not nullBegin"
			+ "Print 'That piece is already in the database'Return 1END"
			+ "insert into pieces (Part_Name, PartNumber)values (@PartName, @PartNumber) Go";

	private String addSetQuery = "CREATE Procedure [AddSet]@SetNumber int,@SetName varchar(40) ,"
			+ "@MinAge int = 0,@MaxAge int = Null,@Theme varchar(40) = 'Other',@Cost money = 0As"
			+ "if(Select SetNumber From LEGO_Sets Where SetNumber = @SetNumber) is not nullBegin"
			+ "Print 'That set is already in the database'Return 1END"
			+ "if(@MinAge<0 or @MaxAge <= @MinAge) BeginPrint 'Invalid age entries'Return 2End"
			+ "if(@Cost<0)BeginPrint 'The cost cannot be negative'Return 3End"
			+ "Insert into LEGO_Sets (SetNumber, SetName, MinAge, MaxAge, Cost,Theme)Values(@SetNumber,"
			+ "@SetName,@MinAge,@MaxAge,@Cost,(Select ThemeID From Themes"
			+ "Where Lower(@Theme)=Lower(Themes.name))) Go";

	private String addSetToCollectionQuery = "Create procedure [addSetToCollection]@Username varchar(20),"
			+ "@SetNumber intAsif (Select username from users where username=@Username) is nullBegin"
			+ "print 'That User does not exist'return 1End"
			+ "if (Select setNumber from LEGO_Sets where SetNumber = @SetNumber) is nullBegin"
			+ "print 'That Set does not exist'return 2End"
			+ "if ((Select OwnsSet.quantity from OwnsSet where OwnsSet.username=@Username and OwnsSet.SetNumber=@SetNumber) is not null)"
			+ "BeginUpdate OwnsSetSet Quantity = OwnsSet.Quantity+1"
			+ "Where Username = @Username and SetNumber = @SetNumberReturn 0End"
			+ "Insert into OwnsSet (Username,SetNumber,Quantity)values (@Username, @SetNumber, 1)Return 0"
			+ " Go";

	private String addToWishlistQuery = "CREATE procedure [AddToWishlist]@Set_Num [int],@Username varchar(40)"
			+ "Asif (Select SetNumber from brunera1.LEGO_Sets where SetNumber = @Set_Num) is nullBegin"
			+ "print 'Set '+ @Set_Num +' does not exist'return 1End"
			+ "if (Select Username from brunera1.Users where Username=@Username) is nullBegin"
			+ "print 'User '+@Username+' does not exist'return 2End"
			+ "if (Select SetNumber from brunera1.WishListedSets where Username=@Username and SetNumber=@Set_Num) is not null"
			+ "Beginprint'Set already in wishlist'return 3End"
			+ "Insert into brunera1.WishListedSets values (@Set_Num,@Username) Go";

	private String removeFromCollectionQuery = "Create Procedure [removeSetFromCollection]@Username varchar(50),"
			+ "@SetNumber intAs"
			+ "if (select quantity from OwnsSet where Username=@Username and SetNumber=@SetNumber)=1Begin"
			+ "Delete from OwnsSetWhere username = @Username and SetNumber=@setNumberEndElseBegin"
			+ "Update OwnsSetSet Quantity = quantity-1where username = @username and SetNumber = @SetNumber"
			+ "ENDGO";

	private String removeFromWishlistQuery = "Create Procedure [RemoveFromWishList]@Username varchar(50),"
			+ "@SetNum intAsDelete from WishListedSetsWhere SetNumber = @SetNum and Username=@Username"
			+ " Go";

	private String registerQuery = "CREATE PROCEDURE [Register]	@Username nvarchar(50),"
			+ "	@PasswordSalt varchar(50),	@PasswordHash varchar(50)ASBEGIN"
			+ "	if @Username is null or @Username = ''	BEGIN		Print 'Username cannot be null or empty.';"
			+ "		RETURN (1)	END	if @PasswordSalt is null or @PasswordSalt = ''	BEGIN"
			+ "		Print 'PasswordSalt cannot be null or empty.';		RETURN (2)	END"
			+ "	if @PasswordHash is null or @PasswordHash = ''	BEGIN"
			+ "		Print 'PasswordHash cannot be null or empty.';		RETURN (3)	END"
			+ "	IF (SELECT COUNT(*) FROM Users          WHERE Username = @Username) = 1	BEGIN"
			+ "      PRINT 'ERROR: Username already exists.';	  RETURN(4)	END"
			+ "	INSERT INTO [Users](Username, PasswordSalt, PasswordHash)"
			+ "	VALUES (@username, @passwordSalt, @passwordHash)ENDGO";

	private String loginQuery = "Create Procedure [Login]@Username varchar(40)As"
			+ "SELECT PasswordSalt,PasswordHash FROM brunera1.Users WHERE Username = @UsernameGO";

	private String getSetsQuery = "Create Procedure [GetAllSets]As"
			+ "Select SetNumber, SetName, cost, Theme from brunera1.LEGO_SetsGO";

	private String getOwnedSetsQuery = "CREATE Procedure [GetOwnedSets]@Username varchar(40)As"
			+ "if (Select Username from brunera1.Users where Username=@Username) is nullBegin"
			+ "print 'User '+@Username+' does not exist'return 1End"
			+ "Select brunera1.LEGO_Sets.SetNumber, SetName,Quantity from brunera1.LEGO_Sets Join brunera1.OwnsSet on OwnsSet.SetNumber = LEGO_Sets.SetNumber Where OwnsSet.Username = @Username"
			+ " Go";

	private String getPiecesQuery = "Create Procedure [ShowAllPieces]As"
			+ "Select PartNumber, Part_Name from brunera1.PiecesGO";

	private String getOwnedPiecesQuery = "Create Procedure [GetOwnedPieces]@Username varchar(40)As"
			+ "if (Select Username from brunera1.Users where Username=@Username) is nullBegin"
			+ "print 'User '+@Username+' does not exist'return 1End"
			+ "Select PartNumber, Quantity, Part_Name, Color from brunera1.AllOwned Where Username= @UsernameGO";

	private String getWishlistQuery = "CREATE procedure [GetWishlist]@Username varchar(40)As"
			+ "if (Select Username from brunera1.Users where Username=@Username) is nullBegin"
			+ "print 'User '+@Username+' does not exist'return 2End"
			+ "Select WishListedSets.SetNumber, SetNamefrom brunera1.WishListedSets "
			+ "Join brunera1.LEGO_Sets on LEGO_Sets.setnumber = WishListedSets.SetNumberwhere Username= @Username"
			+ " Go";

	public CreateNewDB(Connection c) {
		this.c = c;
	}

	public boolean creation(String u, String p) {
		Statement stmt = null;
		try {
			stmt = c.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		username = u;
		password = p;
		try {
			stmt.execute(this.colorQuery);
			stmt.execute(this.themesQuery);
			stmt.execute(this.piecesQuery);
			stmt.execute(this.usersQuery);
			stmt.execute(this.setQuery);
			stmt.execute(this.hasPieceQuery);
			stmt.execute(this.ownsSetQuery);
			stmt.execute(this.ownsPieceQuery);
			stmt.execute(this.wishlistQuery);

			stmt.execute(this.allOwnedQuery);
			stmt.execute(this.allOwnedQueryNoGrouping);

			stmt.execute(this.addColorQuery);
			stmt.execute(this.addThemeQuery);
			stmt.execute(this.addPieceToCollectionQuery);
			stmt.execute(this.addPieceToSetQuery);
			stmt.execute(this.addSetQuery);
			stmt.execute(this.newPieceQuery);
			stmt.execute(this.addSetToCollectionQuery);
			stmt.execute(this.addToWishlistQuery);
			stmt.execute(this.removeFromCollectionQuery);
			stmt.execute(this.removeFromWishlistQuery);
			stmt.execute(this.registerQuery);
			stmt.execute(this.loginQuery);
			stmt.execute(this.getOwnedPiecesQuery);
			stmt.execute(this.getOwnedSetsQuery);
			stmt.execute(this.getPiecesQuery);
			stmt.execute(this.getSetsQuery);
			stmt.execute(this.getWishlistQuery);
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}
}
