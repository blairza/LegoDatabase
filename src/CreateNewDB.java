import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateNewDB {
	private Connection c;
	private String username;
	private String password;
	private String database_name = "Lego_Database_username";

	private String useDBQuery = "Use [database_name] Go";
	private String newDBQuery = "CREATE DATABASE [database_name]\r\n" + " CONTAINMENT = NONE\r\n" + " ON  PRIMARY \r\n"
			+ "( NAME = N'Lego_Database', FILENAME = N'E:\\Database\\MSSQL12.MSSQLSERVER\\MSSQL\\DATA\\database_name.mdf' , SIZE = 5120KB , MAXSIZE = 30720KB , FILEGROWTH = 10%)\r\n" + " LOG ON \r\n"
			+ "( NAME = N'Lego_Database_log', FILENAME = N'E:\\Database\\MSSQL12.MSSQLSERVER\\MSSQL\\DATA\\database_name.ldf' , SIZE = 1024KB , MAXSIZE = 20480KB , FILEGROWTH = 10%)\r\n" + "GO";
	
	private String dropQuery = "Drop Database [Lego_Database_" + username + "]";

//Tables in the database
	private String colorQuery = "SET ANSI_NULLS ON\r\n" + "GO\r\n" + "\r\n" + "SET QUOTED_IDENTIFIER ON\r\n" + "GO\r\n"
			+ "\r\n" + "CREATE TABLE [Colors](\r\n" + "	[ColorName] [varchar](40) NOT NULL,\r\n"
			+ " CONSTRAINT [PK_Colors] PRIMARY KEY CLUSTERED \r\n" + "(\r\n" + "	[ColorName] ASC\r\n"
			+ ")WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]\r\n"
			+ ") ON [PRIMARY]\r\n" + "GO";

	private String hasPieceQuery = "SET ANSI_NULLS ON\r\n" + "GO\r\n" + "\r\n" + "SET QUOTED_IDENTIFIER ON\r\n"
			+ "GO\r\n" + "\r\n" + "CREATE TABLE  [HasPiece](\r\n" + "	[PieceNumber] [varchar](20) NULL,\r\n"
			+ "	[SetNumber] [int] NULL,\r\n" + "	[Color] [varchar](40) NULL,\r\n" + "	[Amount] [int] NULL\r\n"
			+ ") ON [PRIMARY]\r\n" + "GO\r\n" + "\r\n"
			+ "ALTER TABLE  [HasPiece]  WITH CHECK ADD  CONSTRAINT [FK__HasPiece__Color__300424B4] FOREIGN KEY([Color])\r\n"
			+ "REFERENCES  [Colors] ([ColorName])\r\n" + "GO\r\n" + "\r\n"
			+ "ALTER TABLE  [HasPiece] CHECK CONSTRAINT [FK__HasPiece__Color__300424B4]\r\n" + "GO\r\n" + "\r\n"
			+ "ALTER TABLE  [HasPiece]  WITH CHECK ADD  CONSTRAINT [FK__HasPiece__PieceN__2E1BDC42] FOREIGN KEY([PieceNumber])\r\n"
			+ "REFERENCES  [Pieces] ([PartNumber])\r\n" + "GO\r\n" + "\r\n"
			+ "ALTER TABLE  [HasPiece] CHECK CONSTRAINT [FK__HasPiece__PieceN__2E1BDC42]\r\n" + "GO\r\n" + "\r\n"
			+ "ALTER TABLE  [HasPiece]  WITH CHECK ADD  CONSTRAINT [FK__HasPiece__SetNum__2F10007B] FOREIGN KEY([SetNumber])\r\n"
			+ "REFERENCES  [LEGO_Sets] ([SetNumber])\r\n" + "GO\r\n" + "\r\n"
			+ "ALTER TABLE  [HasPiece] CHECK CONSTRAINT [FK__HasPiece__SetNum__2F10007B]\r\n" + "GO";

	private String setQuery = "SET ANSI_NULLS ON\r\n" + "GO\r\n" + "\r\n" + "SET QUOTED_IDENTIFIER ON\r\n" + "GO\r\n"
			+ "\r\n" + "CREATE TABLE  [LEGO_Sets](\r\n" + "	[SetNumber] [int] NOT NULL,\r\n"
			+ "	[MinAge] [int] NOT NULL,\r\n" + "	[MaxAge] [int] NULL,\r\n"
			+ "	[SetName] [varchar](40) NOT NULL,\r\n" + "	[Cost] [money] NOT NULL,\r\n" + "	[Theme] [int] NULL,\r\n"
			+ "PRIMARY KEY CLUSTERED \r\n" + "(\r\n" + "	[SetNumber] ASC\r\n"
			+ ")WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]\r\n"
			+ ") ON [PRIMARY]\r\n" + "GO\r\n" + "\r\n"
			+ "ALTER TABLE  [LEGO_Sets]  WITH CHECK ADD FOREIGN KEY([Theme])\r\n"
			+ "REFERENCES  [Themes] ([ThemeID])\r\n" + "GO";

	private String ownsPieceQuery = "SET ANSI_NULLS ON\r\n" + "GO\r\n" + "\r\n" + "SET QUOTED_IDENTIFIER ON\r\n"
			+ "GO\r\n" + "\r\n" + "CREATE TABLE  [OwnsPiece](\r\n" + "	[PartNumber] [varchar](20) NULL,\r\n"
			+ "	[Username] [varchar](50) NULL,\r\n" + "	[Color] [varchar](40) NULL,\r\n"
			+ "	[Quantity] [int] NULL\r\n" + ") ON [PRIMARY]\r\n" + "GO\r\n" + "\r\n"
			+ "ALTER TABLE  [OwnsPiece]  WITH CHECK ADD  CONSTRAINT [FK__OwnsPiece__Color__34C8D9D1] FOREIGN KEY([Color])\r\n"
			+ "REFERENCES  [Colors] ([ColorName])\r\n" + "GO\r\n" + "\r\n"
			+ "ALTER TABLE  [OwnsPiece] CHECK CONSTRAINT [FK__OwnsPiece__Color__34C8D9D1]\r\n" + "GO\r\n" + "\r\n"
			+ "ALTER TABLE  [OwnsPiece]  WITH CHECK ADD  CONSTRAINT [FK__OwnsPiece__Piece__32E0915F] FOREIGN KEY([PartNumber])\r\n"
			+ "REFERENCES  [Pieces] ([PartNumber])\r\n" + "GO\r\n" + "\r\n"
			+ "ALTER TABLE  [OwnsPiece] CHECK CONSTRAINT [FK__OwnsPiece__Piece__32E0915F]\r\n" + "GO\r\n" + "\r\n"
			+ "ALTER TABLE  [OwnsPiece]  WITH CHECK ADD  CONSTRAINT [FK__OwnsPiece__Usern__33D4B598] FOREIGN KEY([Username])\r\n"
			+ "REFERENCES  [Users] ([Username])\r\n" + "GO\r\n" + "\r\n"
			+ "ALTER TABLE  [OwnsPiece] CHECK CONSTRAINT [FK__OwnsPiece__Usern__33D4B598]\r\n" + "GO";

	private String ownsSetQuery = "SET ANSI_NULLS ON\r\n" + "GO\r\n" + "\r\n" + "SET QUOTED_IDENTIFIER ON\r\n"
			+ "GO\r\n" + "\r\n" + "CREATE TABLE  [OwnsSet](\r\n" + "	[SetNumber] [int] NOT NULL,\r\n"
			+ "	[Username] [varchar](50) NOT NULL,\r\n" + "	[Quantity] [int] NULL,\r\n"
			+ " CONSTRAINT [PK__OwnsSet__3E655D51BD295835] PRIMARY KEY CLUSTERED \r\n" + "(\r\n"
			+ "	[SetNumber] ASC,\r\n" + "	[Username] ASC\r\n"
			+ ")WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]\r\n"
			+ ") ON [PRIMARY]\r\n" + "GO\r\n" + "\r\n"
			+ "ALTER TABLE  [OwnsSet]  WITH CHECK ADD  CONSTRAINT [FK__OwnsSet__SetNumb__619B8048] FOREIGN KEY([SetNumber])\r\n"
			+ "REFERENCES  [LEGO_Sets] ([SetNumber])\r\n" + "GO\r\n" + "\r\n"
			+ "ALTER TABLE  [OwnsSet] CHECK CONSTRAINT [FK__OwnsSet__SetNumb__619B8048]\r\n" + "GO\r\n" + "\r\n"
			+ "ALTER TABLE  [OwnsSet]  WITH CHECK ADD  CONSTRAINT [FK__OwnsSet__Usernam__60A75C0F] FOREIGN KEY([Username])\r\n"
			+ "REFERENCES  [Users] ([Username])\r\n" + "GO\r\n" + "\r\n"
			+ "ALTER TABLE  [OwnsSet] CHECK CONSTRAINT [FK__OwnsSet__Usernam__60A75C0F]\r\n" + "GO";

	private String piecesQuery = "SET ANSI_NULLS ON\r\n" + "GO\r\n" + "\r\n" + "SET QUOTED_IDENTIFIER ON\r\n" + "GO\r\n"
			+ "\r\n" + "CREATE TABLE  [Pieces](\r\n" + "	[PartNumber] [varchar](20) NOT NULL,\r\n"
			+ "	[Part_Name] [varchar](100) NOT NULL,\r\n"
			+ " CONSTRAINT [PK__Pieces__7BB41F31DCB256E5] PRIMARY KEY CLUSTERED \r\n" + "(\r\n"
			+ "	[PartNumber] ASC\r\n"
			+ ")WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]\r\n"
			+ ") ON [PRIMARY]\r\n" + "GO\r\n";

	private String themesQuery = "SET ANSI_NULLS ON\r\n" + "GO\r\n" + "\r\n" + "SET QUOTED_IDENTIFIER ON\r\n" + "GO\r\n"
			+ "\r\n" + "CREATE TABLE  [Themes](\r\n" + "	[Name] [varchar](40) NOT NULL,\r\n"
			+ "	[ThemeID] [int] IDENTITY(1,1) NOT NULL,\r\n" + "PRIMARY KEY CLUSTERED \r\n" + "(\r\n"
			+ "	[ThemeID] ASC\r\n"
			+ ")WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]\r\n"
			+ ") ON [PRIMARY]\r\n" + "GO";

	private String usersQuery = "SET ANSI_NULLS ON\r\n" + "GO\r\n" + "\r\n" + "SET QUOTED_IDENTIFIER ON\r\n" + "GO\r\n"
			+ "\r\n" + "CREATE TABLE  [Users](\r\n" + "	[Username] [varchar](50) NOT NULL,\r\n"
			+ "	[PasswordHash] [varchar](50) NOT NULL,\r\n" + "	[PasswordSalt] [varchar](50) NOT NULL,\r\n"
			+ " CONSTRAINT [PK__Users__536C85E59B32A24D] PRIMARY KEY CLUSTERED \r\n" + "(\r\n" + "	[Username] ASC\r\n"
			+ ")WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]\r\n"
			+ ") ON [PRIMARY]\r\n" + "GO";

	private String wishlistQuery = "SET ANSI_NULLS ON\r\n" + "GO\r\n" + "\r\n" + "SET QUOTED_IDENTIFIER ON\r\n"
			+ "GO\r\n" + "\r\n" + "CREATE TABLE  [WishListedSets](\r\n" + "	[SetNumber] [int] NOT NULL,\r\n"
			+ "	[Username] [varchar](50) NOT NULL,\r\n" + "PRIMARY KEY CLUSTERED \r\n" + "(\r\n"
			+ "	[SetNumber] ASC,\r\n" + "	[Username] ASC\r\n"
			+ ")WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]\r\n"
			+ ") ON [PRIMARY]\r\n" + "GO\r\n" + "\r\n"
			+ "ALTER TABLE  [WishListedSets]  WITH CHECK ADD FOREIGN KEY([Username])\r\n"
			+ "REFERENCES  [Users] ([Username])\r\n" + "GO";

//Views in the database
	private String allOwnedQuery = "SET ANSI_NULLS ON\r\n" + "GO\r\n" + "\r\n" + "SET QUOTED_IDENTIFIER ON\r\n"
			+ "GO\r\n" + "\r\n" + "\r\n" + "\r\n" + "\r\n" + "CREATE View [AllOwned] As\r\n" + "SELECT [Username]\r\n"
			+ "      ,[partNumber]\r\n" + "      ,[Color]\r\n" + "     ,Sum([Quantity]) as Quantity,\r\n"
			+ "	 Part_Name\r\n" + "  FROM [Lego_Database].[AllOwned_NoGrouping]\r\n"
			+ "  Group By Username, PartNumber, Color, Part_Name\r\n" + "GO";

	private String allOwnedQueryNoGrouping = "SET ANSI_NULLS ON\r\n" + "GO\r\n" + "\r\n"
			+ "SET QUOTED_IDENTIFIER ON\r\n" + "GO\r\n" + "\r\n" + "\r\n" + "\r\n" + "\r\n"
			+ "CREATE View [AllOwned_NoGrouping] as\r\n"
			+ "Select Username, pieces.partNumber, Color, Sum(Quantity) As Quantity, Part_Name\r\n"
			+ "From OwnsPiece\r\n" + "Join Pieces on Pieces.PartNumber = OwnsPiece.PartNumber\r\n"
			+ "Group By Username, pieces.partNumber, Color, Part_Name\r\n" + "Union All\r\n"
			+ "Select OwnsSet.Username, hasPiece.PieceNumber, HasPiece.Color, HasPiece.Amount*OwnsSet.Quantity, Part_Name\r\n"
			+ "From OwnsSet\r\n" + "Join HasPiece on ownsset.SetNumber = haspiece.SetNumber\r\n"
			+ "Join Pieces on HasPiece.PieceNumber = Pieces.PartNumber\r\n" + "\r\n" + "GO";

//Stored Procedures in the database
	private String addColorQuery = "SET ANSI_NULLS ON\r\n" + "GO\r\n" + "\r\n" + "SET QUOTED_IDENTIFIER ON\r\n"
			+ "GO\r\n" + "\r\n" + "\r\n" + "Create procedure [addColor]\r\n" + "@ColorName varchar(40)\r\n" + "As\r\n"
			+ "\r\n" + "if((Select count(ColorName) from Colors where ColorName =@ColorName)=0)\r\n" + "Begin\r\n"
			+ "insert into Colors(ColorName)\r\n" + "Values(@ColorName)\r\n" + "End\r\n" + "GO";

	private String addThemeQuery = "SET ANSI_NULLS ON\r\n" + "GO\r\n" + "\r\n" + "SET QUOTED_IDENTIFIER ON\r\n"
			+ "GO\r\n" + "\r\n" + "\r\n" + "CREATE Procedure [AddTheme]\r\n" + "@ThemeName varchar(40)\r\n" + "\r\n"
			+ "\r\n" + "AS\r\n" + "\r\n" + "IF (Select ThemeID from Themes where Name=@ThemeName) is Not NULL\r\n"
			+ "Begin\r\n" + "Print 'That Theme already exists'\r\n" + "Return 1;\r\n" + "END\r\n" + "\r\n" + "\r\n"
			+ "Begin\r\n" + "Insert into Themes (Name)\r\n" + "Values (@ThemeName)\r\n" + "End\r\n" + "GO";

	private String addPieceToCollectionQuery = "SET ANSI_NULLS ON\r\n" + "GO\r\n" + "\r\n"
			+ "SET QUOTED_IDENTIFIER ON\r\n" + "GO\r\n" + "\r\n" + "CREATE procedure [AddPieceToCollection]\r\n"
			+ "@Username varchar(20),\r\n" + "@Color varchar(40),\r\n" + "@PartNumber varchar(20),\r\n"
			+ "@Quantity int\r\n" + "As\r\n" + "\r\n"
			+ "if (Select Part_Name from pieces where PartNumber=@PartNumber) is null\r\n" + "Begin\r\n"
			+ "print 'piece '+@PartNumber+' does not exist'\r\n" + "return 1\r\n" + "End\r\n" + "\r\n"
			+ "if (Select Username from Users where Username=@Username) is null\r\n" + "Begin\r\n"
			+ "print 'User '+@Username+' does not exist'\r\n" + "return 2\r\n" + "End\r\n" + "\r\n"
			+ "if (@Quantity <= 0)\r\n" + "Begin\r\n" + "print 'Quantity cannot be nagative'\r\n" + "return 3\r\n"
			+ "End\r\n" + "\r\n" + "if (Select ColorName from Colors where LOWER(ColorName)=LOWER(@Color)) is null\r\n"
			+ "Begin\r\n" + "print 'color '+@Color+' does not exist'\r\n" + "return 4\r\n" + "End\r\n" + "\r\n"
			+ "if (select quantity from ownsPiece where Username=@username and Lower(color) = Lower(@Color) and partNumber = @PartNumber) is not null\r\n"
			+ "Begin\r\n" + "Update OwnsPiece\r\n" + "Set Quantity = Quantity+@Quantity\r\n"
			+ "Where Username=@username and color = @Color and partNumber = @PartNumber\r\n" + "return 0\r\n"
			+ "End\r\n" + "\r\n" + "insert into OwnsPiece (Username, color, partnumber, quantity)\r\n"
			+ "values (@Username, @Color, @PartNumber, @Quantity)\r\n" + "GO";

	private String addPieceToSetQuery = "SET ANSI_NULLS ON\r\n" + "GO\r\n" + "\r\n" + "SET QUOTED_IDENTIFIER ON\r\n"
			+ "GO\r\n" + "\r\n" + "\r\n" + "CREATE Procedure [AddToSet]\r\n" + "@PieceNumber varchar(20),\r\n"
			+ "@SetNumber int,\r\n" + "@count int = 1,\r\n" + "@color varchar(20)\r\n" + "As\r\n" + "if\r\n"
			+ "(select PieceNumber\r\n" + "from HasPiece\r\n"
			+ "where @PieceNumber = PieceNumber and @SetNumber = SetNumber and @color = Color\r\n" + ") is not null\r\n"
			+ "Begin\r\n" + "Update HasPiece\r\n" + "Set Amount = Amount + @count\r\n"
			+ "where PieceNumber = @PieceNumber and @SetNumber = SetNumber and Color = @color\r\n" + "return 0\r\n"
			+ "End\r\n" + "\r\n" + "if\r\n" + "(select PartNumber\r\n" + "from Pieces\r\n"
			+ "where PartNumber=@PieceNumber) is null\r\n" + "Begin\r\n" + "Print 'That piece does not exist'\r\n"
			+ "return 1\r\n" + "End\r\n" + "\r\n" + "if\r\n" + "(select SetNumber\r\n" + "from LEGO_Sets\r\n"
			+ "where @SetNumber = SetNumber) is null\r\n" + "begin\r\n" + "Print 'That set does not exist'\r\n"
			+ "return 1\r\n" + "End\r\n" + "\r\n" + "if\r\n" + "(select ColorName\r\n" + "from Colors\r\n"
			+ "where LOWER(ColorName)=Lower(@color)) is null\r\n" + "Begin\r\n"
			+ "Print 'That color does not exist'\r\n" + "return 1\r\n" + "end\r\n" + "\r\n" + "if(@count<1)\r\n"
			+ "Begin\r\n" + "Print 'Cannot have non-positive amount'\r\n" + "return 1\r\n" + "End\r\n" + "\r\n"
			+ "insert into HasPiece(SetNumber,PieceNumber,Amount,Color)\r\n"
			+ "values (@SetNumber,@PieceNumber,@count,@color)\r\n" + "GO";

	private String newPieceQuery = "SET ANSI_NULLS ON\r\n" + "GO\r\n" + "\r\n" + "SET QUOTED_IDENTIFIER ON\r\n"
			+ "GO\r\n" + "\r\n" + "\r\n" + "Create Procedure [NewPiece]\r\n" + "@PartNumber varchar(20),\r\n"
			+ "@PartName varchar(40)\r\n" + "As\r\n" + "\r\n"
			+ "if (Select Part_Name From Pieces Where @PartNumber=PartNumber) is not null\r\n" + "Begin\r\n"
			+ "Print 'That piece is already in the database'\r\n" + "Return 1\r\n" + "END\r\n" + "\r\n"
			+ "insert into pieces (Part_Name, PartNumber)\r\n" + "values (@PartName, @PartNumber)\r\n" + "GO";

	private String addSetQuery = "SET ANSI_NULLS ON\r\n" + "GO\r\n" + "\r\n" + "SET QUOTED_IDENTIFIER ON\r\n" + "GO\r\n"
			+ "\r\n" + "\r\n" + "CREATE Procedure [AddSet]\r\n" + "@SetNumber int,\r\n" + "@SetName varchar(40) ,\r\n"
			+ "@MinAge int = 0,\r\n" + "@MaxAge int = Null,\r\n" + "@Theme varchar(40) = 'Other',\r\n"
			+ "@Cost money = 0\r\n" + "As\r\n"
			+ "if(Select SetNumber From LEGO_Sets Where SetNumber = @SetNumber) is not null\r\n" + "Begin\r\n"
			+ "Print 'That set is already in the database'\r\n" + "Return 1\r\n" + "END\r\n"
			+ "if(@MinAge<0 or @MaxAge <= @MinAge) \r\n" + "Begin\r\n" + "Print 'Invalid age entries'\r\n"
			+ "Return 2\r\n" + "End\r\n" + "if(@Cost<0)\r\n" + "Begin\r\n" + "Print 'The cost cannot be negative'\r\n"
			+ "Return 3\r\n" + "End\r\n" + "Insert into LEGO_Sets (SetNumber, SetName, MinAge, MaxAge, Cost,Theme)\r\n"
			+ "Values(\r\n" + "@SetNumber,\r\n" + "@SetName,\r\n" + "@MinAge,\r\n" + "@MaxAge,\r\n" + "@Cost,\r\n"
			+ "(Select ThemeID From Themes\r\n" + "Where Lower(@Theme)=Lower(Themes.name))\r\n" + ")\r\n" + "GO";

	private String addSetToCollectionQuery = "SET ANSI_NULLS ON\r\n" + "GO\r\n" + "\r\n"
			+ "SET QUOTED_IDENTIFIER ON\r\n" + "GO\r\n" + "\r\n" + "create procedure [addSetToCollection]\r\n"
			+ "@Username varchar(20),\r\n" + "@SetNumber int\r\n" + "As\r\n" + "\r\n"
			+ "if (Select username from users where username=@Username) is null\r\n" + "Begin\r\n"
			+ "print 'That User does not exist'\r\n" + "return 1\r\n" + "End\r\n" + "\r\n"
			+ "if (Select setNumber from LEGO_Sets where SetNumber = @SetNumber) is null\r\n" + "Begin\r\n"
			+ "print 'That Set does not exist'\r\n" + "return 2\r\n" + "End\r\n" + "\r\n"
			+ "if ((Select OwnsSet.quantity from OwnsSet where OwnsSet.username=@Username and OwnsSet.SetNumber=@SetNumber) is not null)\r\n"
			+ "Begin\r\n" + "Update OwnsSet\r\n" + "Set Quantity = OwnsSet.Quantity+1\r\n"
			+ "Where Username = @Username and SetNumber = @SetNumber\r\n" + "Return 0\r\n" + "End\r\n" + "\r\n"
			+ "Insert into OwnsSet (Username,SetNumber,Quantity)\r\n" + "values (@Username, @SetNumber, 1)\r\n"
			+ "Return 0\r\n" + "GO";

	private String addToWishlistQuery = "SET ANSI_NULLS ON\r\n" + "GO\r\n" + "\r\n" + "SET QUOTED_IDENTIFIER ON\r\n"
			+ "GO\r\n" + "\r\n" + "CREATE procedure [AddToWishlist]\r\n" + "@Set_Num [int],\r\n"
			+ "@Username varchar(40)\r\n" + "As\r\n" + "\r\n"
			+ "if (Select SetNumber from brunera1.LEGO_Sets where SetNumber = @Set_Num) is null\r\n" + "Begin\r\n"
			+ "print 'Set '+ @Set_Num +' does not exist'\r\n" + "return 1\r\n" + "End\r\n" + "\r\n"
			+ "if (Select Username from brunera1.Users where Username=@Username) is null\r\n" + "Begin\r\n"
			+ "print 'User '+@Username+' does not exist'\r\n" + "return 2\r\n" + "End\r\n" + "\r\n"
			+ "if (Select SetNumber from brunera1.WishListedSets where Username=@Username and SetNumber=@Set_Num) is not null\r\n"
			+ "Begin\r\n" + "print'Set already in wishlist'\r\n" + "return 3\r\n" + "End\r\n"
			+ "Insert into brunera1.WishListedSets values (@Set_Num,@Username)\r\n" + "GO";

	private String removeFromCollectionQuery = "SET ANSI_NULLS ON\r\n" + "GO\r\n" + "\r\n"
			+ "SET QUOTED_IDENTIFIER ON\r\n" + "GO\r\n" + "\r\n" + "\r\n"
			+ "Create Procedure [removeSetFromCollection]\r\n" + "@Username varchar(50),\r\n" + "@SetNumber int\r\n"
			+ "As\r\n" + "\r\n"
			+ "if (select quantity from OwnsSet where Username=@Username and SetNumber=@SetNumber)=1\r\n" + "Begin\r\n"
			+ "Delete from OwnsSet\r\n" + "Where username = @Username and SetNumber=@setNumber\r\n" + "End\r\n"
			+ "Else\r\n" + "Begin\r\n" + "Update OwnsSet\r\n" + "Set Quantity = quantity-1\r\n"
			+ "where username = @username and SetNumber = @SetNumber\r\n" + "END\r\n" + "GO";

	private String removeFromWishlistQuery = "SET ANSI_NULLS ON\r\n" + "GO\r\n" + "\r\n"
			+ "SET QUOTED_IDENTIFIER ON\r\n" + "GO\r\n" + "\r\n" + "Create Procedure [RemoveFromWishList]\r\n"
			+ "@Username varchar(50),\r\n" + "@SetNum int\r\n" + "As\r\n" + "\r\n" + "Delete from WishListedSets\r\n"
			+ "Where SetNumber = @SetNum and Username=@Username\r\n" + "GO";

	private String registerQuery = "SET ANSI_NULLS ON\r\n" + "GO\r\n" + "\r\n" + "SET QUOTED_IDENTIFIER ON\r\n"
			+ "GO\r\n" + "\r\n" + "CREATE PROCEDURE [Register]\r\n" + "	@Username nvarchar(50),\r\n"
			+ "	@PasswordSalt varchar(50),\r\n" + "	@PasswordHash varchar(50)\r\n" + "AS\r\n" + "BEGIN\r\n"
			+ "	if @Username is null or @Username = ''\r\n" + "	BEGIN\r\n"
			+ "		Print 'Username cannot be null or empty.';\r\n" + "		RETURN (1)\r\n" + "	END\r\n"
			+ "	if @PasswordSalt is null or @PasswordSalt = ''\r\n" + "	BEGIN\r\n"
			+ "		Print 'PasswordSalt cannot be null or empty.';\r\n" + "		RETURN (2)\r\n" + "	END\r\n"
			+ "	if @PasswordHash is null or @PasswordHash = ''\r\n" + "	BEGIN\r\n"
			+ "		Print 'PasswordHash cannot be null or empty.';\r\n" + "		RETURN (3)\r\n" + "	END\r\n"
			+ "	IF (SELECT COUNT(*) FROM Users\r\n" + "          WHERE Username = @Username) = 1\r\n" + "	BEGIN\r\n"
			+ "      PRINT 'ERROR: Username already exists.';\r\n" + "	  RETURN(4)\r\n" + "	END\r\n"
			+ "	INSERT INTO [Users](Username, PasswordSalt, PasswordHash)\r\n"
			+ "	VALUES (@username, @passwordSalt, @passwordHash)\r\n" + "END\r\n" + "GO";

	private String loginQuery = "SET ANSI_NULLS ON\r\n" + "GO\r\n" + "\r\n" + "SET QUOTED_IDENTIFIER ON\r\n" + "GO\r\n"
			+ "\r\n" + "\r\n" + "Create Procedure [Login]\r\n" + "@Username varchar(40)\r\n" + "As\r\n"
			+ "SELECT PasswordSalt,PasswordHash FROM brunera1.Users WHERE Username = @Username\r\n" + "GO";

	private String getSetsQuery = "SET ANSI_NULLS ON\r\n" + "GO\r\n" + "\r\n" + "SET QUOTED_IDENTIFIER ON\r\n"
			+ "GO\r\n" + "\r\n" + "Create Procedure [GetAllSets]\r\n" + "As\r\n"
			+ "Select SetNumber, SetName, cost, Theme from brunera1.LEGO_Sets\r\n" + "GO";

	private String getOwnedSetsQuery = "SET ANSI_NULLS ON\r\n" + "GO\r\n" + "\r\n" + "SET QUOTED_IDENTIFIER ON\r\n"
			+ "GO\r\n" + "\r\n" + "CREATE Procedure [GetOwnedSets]\r\n" + "@Username varchar(40)\r\n" + "As\r\n"
			+ "\r\n" + "if (Select Username from brunera1.Users where Username=@Username) is null\r\n" + "Begin\r\n"
			+ "print 'User '+@Username+' does not exist'\r\n" + "return 1\r\n" + "End\r\n" + "\r\n"
			+ "Select brunera1.LEGO_Sets.SetNumber, SetName,Quantity from brunera1.LEGO_Sets Join brunera1.OwnsSet on OwnsSet.SetNumber = LEGO_Sets.SetNumber Where OwnsSet.Username = @Username\r\n"
			+ "GO";

	private String getPiecesQuery = "SET ANSI_NULLS ON\r\n" + "GO\r\n" + "\r\n" + "SET QUOTED_IDENTIFIER ON\r\n"
			+ "GO\r\n" + "\r\n" + "Create Procedure [ShowAllPieces]\r\n" + "As\r\n"
			+ "Select PartNumber, Part_Name from brunera1.Pieces\r\n" + "GO";

	private String getOwnedPiecesQuery = "SET ANSI_NULLS ON\r\n" + "GO\r\n" + "\r\n" + "SET QUOTED_IDENTIFIER ON\r\n"
			+ "GO\r\n" + "\r\n" + "Create Procedure [GetOwnedPieces]\r\n" + "@Username varchar(40)\r\n" + "As\r\n"
			+ "\r\n" + "if (Select Username from brunera1.Users where Username=@Username) is null\r\n" + "Begin\r\n"
			+ "print 'User '+@Username+' does not exist'\r\n" + "return 1\r\n" + "End\r\n" + "\r\n"
			+ "Select PartNumber, Quantity, Part_Name, Color from brunera1.AllOwned Where Username= @Username\r\n"
			+ "GO";

	private String getWishlistQuery = "SET ANSI_NULLS ON\r\n" + "GO\r\n" + "\r\n" + "SET QUOTED_IDENTIFIER ON\r\n"
			+ "GO\r\n" + "\r\n" + "CREATE procedure [GetWishlist]\r\n" + "@Username varchar(40)\r\n" + "As\r\n" + "\r\n"
			+ "if (Select Username from brunera1.Users where Username=@Username) is null\r\n" + "Begin\r\n"
			+ "print 'User '+@Username+' does not exist'\r\n" + "return 2\r\n" + "End\r\n" + "\r\n"
			+ "Select WishListedSets.SetNumber, SetName\r\n" + "from brunera1.WishListedSets \r\n"
			+ "Join brunera1.LEGO_Sets on LEGO_Sets.setnumber = WishListedSets.SetNumber\r\n"
			+ "where Username= @Username\r\n" + "GO";

	public CreateNewDB(Connection c) {
		this.c = c;
	}
	

	public boolean creation(String u,String p) {
		Statement stmt = null;
		try {
			stmt = c.createStatement();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		username = u;
		password = p;
		this.database_name = this.database_name.replaceAll("username", username);
		this.useDBQuery = this.useDBQuery.replaceAll("database_name",database_name);
		this.newDBQuery = this.newDBQuery.replaceAll("database_name", database_name);
		try {
			try {
			stmt.execute(this.newDBQuery);
			}catch(SQLException e){
				stmt.execute(this.dropQuery);
				stmt.execute(this.newDBQuery);
			}
			stmt.execute(this.useDBQuery);
			stmt.execute(this.colorQuery);
			stmt.execute(this.hasPieceQuery);
			stmt.execute(this.setQuery);
			stmt.execute(this.ownsSetQuery);
			stmt.execute(this.ownsPieceQuery);
			stmt.execute(this.piecesQuery);
			stmt.execute(this.themesQuery);
			stmt.execute(this.usersQuery);
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
			System.out.println("Database: " + this.database_name + " successfully created");
			return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return false;
	}
}
