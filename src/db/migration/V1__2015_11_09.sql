USE [master]
GO
/****** Object:  Database [Anynote]    Script Date: 2015/11/9 星期一 下午 1:54:11 ******/
CREATE DATABASE [Anynote]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'Anynote', FILENAME = N'F:\SQLServerData\data\Anynote.mdf' , SIZE = 57344KB , MAXSIZE = UNLIMITED, FILEGROWTH = 1024KB )
 LOG ON 
( NAME = N'Anynote_log', FILENAME = N'F:\SQLServerData\data\Anynote_log.ldf' , SIZE = 69760KB , MAXSIZE = 2048GB , FILEGROWTH = 10%)
GO
ALTER DATABASE [Anynote] SET COMPATIBILITY_LEVEL = 90
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [Anynote].[dbo].[sp_fulltext_database] @action = 'disable'
end
GO
ALTER DATABASE [Anynote] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [Anynote] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [Anynote] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [Anynote] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [Anynote] SET ARITHABORT OFF 
GO
ALTER DATABASE [Anynote] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [Anynote] SET AUTO_CREATE_STATISTICS ON 
GO
ALTER DATABASE [Anynote] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [Anynote] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [Anynote] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [Anynote] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [Anynote] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [Anynote] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [Anynote] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [Anynote] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [Anynote] SET  DISABLE_BROKER 
GO
ALTER DATABASE [Anynote] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [Anynote] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [Anynote] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [Anynote] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [Anynote] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [Anynote] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [Anynote] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [Anynote] SET RECOVERY FULL 
GO
ALTER DATABASE [Anynote] SET  MULTI_USER 
GO
ALTER DATABASE [Anynote] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [Anynote] SET DB_CHAINING OFF 
GO
ALTER DATABASE [Anynote] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [Anynote] SET TARGET_RECOVERY_TIME = 0 SECONDS 
GO
EXEC sys.sp_db_vardecimal_storage_format N'Anynote', N'ON'
GO
USE [Anynote]
GO
/****** Object:  StoredProcedure [dbo].[Backup_DataBase]    Script Date: 2015/11/9 星期一 下午 1:54:11 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE proc [dbo].[Backup_DataBase] 
        @databasename varchar(50),
		@path varchar(100)
as
  BackUp DataBase  @databasename  To Disk=  @path   with init
GO
/****** Object:  StoredProcedure [dbo].[database_backup]    Script Date: 2015/11/9 星期一 下午 1:54:11 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
create proc [dbo].[database_backup]
 @databasename varchar(50),@path varchar(150)
as
 BACKUP DATABASE @databasename
 TO DISK = @path
GO
/****** Object:  Table [dbo].[an_account]    Script Date: 2015/11/9 星期一 下午 1:54:11 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[an_account](
	[ACCOUNT_ID] [int] IDENTITY(1,1) NOT NULL,
	[ACCOUNT_BOOK_ID] [int] NOT NULL,
	[CATEGORY_ID] [int] NOT NULL,
	[ACCOUNT_DATE] [datetime] NOT NULL,
	[ACCOUNT_TYPE] [char](1) NOT NULL,
	[MONEY] [money] NOT NULL,
	[REMARK] [varchar](100) NULL,
	[STATUS] [char](1) NOT NULL,
	[DELFLAG] [char](1) NOT NULL,
	[CREATE_USER] [varchar](20) NOT NULL,
	[CREATE_TIME] [datetime] NOT NULL,
	[UPDATE_USER] [varchar](20) NOT NULL,
	[UPDATE_TIME] [datetime] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[ACCOUNT_ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[an_account_book]    Script Date: 2015/11/9 星期一 下午 1:54:11 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[an_account_book](
	[ACCOUNT_BOOK_ID] [int] IDENTITY(1,1) NOT NULL,
	[ACCOUNT_BOOK_NAME] [varchar](20) NOT NULL,
	[STATUS] [char](1) NOT NULL,
	[DELFLAG] [char](1) NOT NULL,
	[CREATE_USER] [varchar](20) NOT NULL,
	[CREATE_TIME] [datetime] NOT NULL,
	[UPDATE_USER] [varchar](20) NOT NULL,
	[UPDATE_TIME] [datetime] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[ACCOUNT_BOOK_ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[an_account_category]    Script Date: 2015/11/9 星期一 下午 1:54:11 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[an_account_category](
	[CATEGORY_ID] [int] IDENTITY(1,1) NOT NULL,
	[CATEGORY_NAME] [varchar](20) NOT NULL,
	[STATUS] [char](1) NOT NULL,
	[DELFLAG] [char](1) NOT NULL,
	[CREATE_USER] [varchar](20) NOT NULL,
	[CREATE_TIME] [datetime] NOT NULL,
	[UPDATE_USER] [varchar](20) NOT NULL,
	[UPDATE_TIME] [datetime] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[CATEGORY_ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[an_address_book]    Script Date: 2015/11/9 星期一 下午 1:54:11 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[an_address_book](
	[ab_id] [varchar](40) NOT NULL,
	[ab_name] [varchar](50) NOT NULL,
	[ab_level] [varchar](4) NOT NULL,
	[ab_sex] [varchar](2) NULL,
	[ab_age] [int] NULL,
	[ab_birthday] [datetime] NULL,
	[ab_education] [varchar](5) NULL,
	[ab_native_place] [varchar](20) NULL,
	[ab_address] [varchar](50) NULL,
	[ab_company_name] [varchar](15) NULL,
	[ab_company_address] [varchar](50) NULL,
	[ab_position] [varchar](50) NULL,
	[ab_tel] [varchar](15) NOT NULL,
	[ab_qq] [varchar](15) NULL,
	[ab_wx] [varchar](20) NULL,
 CONSTRAINT [PK_an_address_book] PRIMARY KEY CLUSTERED 
(
	[ab_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[an_album]    Script Date: 2015/11/9 星期一 下午 1:54:11 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[an_album](
	[ALBUM_ID] [int] IDENTITY(1,1) NOT NULL,
	[ALBUM_NAME] [varchar](20) NOT NULL,
	[PARENT_ID] [int] NOT NULL,
	[ISLEAF] [char](1) NOT NULL,
	[STATUS] [char](1) NOT NULL,
	[DELFLAG] [char](1) NOT NULL,
	[CREATE_USER] [varchar](20) NOT NULL,
	[CREATE_TIME] [datetime] NOT NULL,
	[UPDATE_USER] [varchar](20) NOT NULL,
	[UPDATE_TIME] [datetime] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[ALBUM_ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[an_attendence_time]    Script Date: 2015/11/9 星期一 下午 1:54:11 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[an_attendence_time](
	[at_id] [varchar](32) NULL,
	[at_am_on_time] [varchar](20) NULL,
	[at_am_off_time] [varchar](20) NULL,
	[at_pm_on_time] [varchar](20) NULL,
	[at_pm_off_time] [varchar](20) NULL
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[an_attendence_time_record]    Script Date: 2015/11/9 星期一 下午 1:54:11 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[an_attendence_time_record](
	[atr_id] [varchar](32) NOT NULL,
	[atr_am_on_time] [datetime] NULL,
	[atr_am_off_time] [datetime] NULL,
	[atr_pm_on_time] [datetime] NULL,
	[atr_pm_off_time] [datetime] NULL,
	[atr_user_id] [varchar](32) NOT NULL
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[an_common_web_site]    Script Date: 2015/11/9 星期一 下午 1:54:11 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[an_common_web_site](
	[cws_id] [varchar](40) NOT NULL,
	[cws_user_id] [varchar](40) NULL,
	[cws_user_name] [varchar](20) NULL,
	[cws_date] [smalldatetime] NULL,
	[cws_site_name] [varchar](20) NULL,
	[cws_site_url] [varchar](100) NULL,
	[cws_count] [int] NULL,
	[cws_remark] [varchar](200) NULL,
 CONSTRAINT [PK_an_common_web_site] PRIMARY KEY CLUSTERED 
(
	[cws_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[an_document]    Script Date: 2015/11/9 星期一 下午 1:54:11 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[an_document](
	[DOCUMENT_ID] [int] IDENTITY(1,1) NOT NULL,
	[DOCUMENT_NAME] [varchar](255) NOT NULL,
	[LINK] [varchar](200) NULL,
	[TYPE] [varchar](50) NULL,
	[SIZE] [int] NULL,
	[TAGS] [varchar](255) NULL,
	[PARENT_ID] [int] NOT NULL,
	[ISLEAF] [char](1) NOT NULL,
	[STATUS] [char](1) NOT NULL,
	[DELFLAG] [char](1) NOT NULL,
	[CREATE_USER] [varchar](20) NOT NULL,
	[CREATE_TIME] [datetime] NOT NULL,
	[UPDATE_USER] [varchar](20) NOT NULL,
	[UPDATE_TIME] [datetime] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[DOCUMENT_ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[an_feed]    Script Date: 2015/11/9 星期一 下午 1:54:11 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[an_feed](
	[FEED_ID] [int] IDENTITY(1,1) NOT NULL,
	[FEED_NAME] [varchar](255) NOT NULL,
	[FEED_URL] [varchar](255) NULL,
	[FEED_COUNT] [int] NOT NULL,
	[PARENT_ID] [int] NOT NULL,
	[ISLEAF] [char](1) NOT NULL,
	[STATUS] [char](1) NOT NULL,
	[DELFLAG] [char](1) NOT NULL,
	[CREATE_USER] [varchar](20) NOT NULL,
	[CREATE_TIME] [datetime] NOT NULL,
	[UPDATE_USER] [varchar](20) NOT NULL,
	[UPDATE_TIME] [datetime] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[FEED_ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[an_feed_favorite]    Script Date: 2015/11/9 星期一 下午 1:54:11 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[an_feed_favorite](
	[FEED_ID] [int] IDENTITY(1,1) NOT NULL,
	[TITLE] [varchar](200) NULL,
	[LINK] [varchar](200) NULL,
	[DESCRIPTION] [text] NULL,
	[UPDATED] [datetime] NULL,
	[STATUS] [char](1) NOT NULL,
	[DELFLAG] [char](1) NOT NULL,
	[CREATE_USER] [varchar](20) NOT NULL,
	[CREATE_TIME] [datetime] NOT NULL,
	[UPDATE_USER] [varchar](20) NOT NULL,
	[UPDATE_TIME] [datetime] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[FEED_ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[an_holidy]    Script Date: 2015/11/9 星期一 下午 1:54:11 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[an_holidy](
	[h_id] [varchar](32) NOT NULL,
	[h_name] [varchar](32) NOT NULL,
	[h_start] [datetime] NOT NULL,
	[h_end] [datetime] NOT NULL,
	[at_id] [varchar](32) NOT NULL,
 CONSTRAINT [PK_an_holidy] PRIMARY KEY CLUSTERED 
(
	[h_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[an_html_content]    Script Date: 2015/11/9 星期一 下午 1:54:11 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[an_html_content](
	[hc_id] [varchar](40) NOT NULL,
	[hc_user_name] [varchar](40) NULL,
	[hc_date] [smalldatetime] NULL,
	[hc_url] [varchar](100) NULL,
	[hc_code] [varchar](50) NULL,
	[hc_size] [varchar](50) NULL,
	[hc_cast_time] [varchar](50) NULL,
	[hc_content] [text] NULL,
	[hc_remark] [varchar](200) NULL,
 CONSTRAINT [PK_an_html_content] PRIMARY KEY CLUSTERED 
(
	[hc_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[an_login_history]    Script Date: 2015/11/9 星期一 下午 1:54:11 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[an_login_history](
	[lh_id] [varchar](32) NOT NULL,
	[lh_login_user_name] [varchar](32) NULL,
	[lh_login_user_id] [varchar](32) NULL,
	[lh_login_time] [datetime] NULL,
	[lh_login_out] [datetime] NULL,
	[lh_login_sum_time] [bigint] NULL,
	[lh_login_ip] [varchar](32) NULL,
 CONSTRAINT [PK_AN_LOGIN_HISTORY] PRIMARY KEY CLUSTERED 
(
	[lh_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[an_note]    Script Date: 2015/11/9 星期一 下午 1:54:11 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[an_note](
	[NOTE_ID] [int] IDENTITY(1,1) NOT NULL,
	[CATEGORY_ID] [int] NOT NULL,
	[TITLE] [varchar](100) NOT NULL,
	[CONTENT] [text] NOT NULL,
	[STATUS] [char](1) NOT NULL,
	[DELFLAG] [char](1) NOT NULL,
	[CREATE_USER] [varchar](20) NOT NULL,
	[CREATE_TIME] [datetime] NOT NULL,
	[UPDATE_USER] [varchar](20) NOT NULL,
	[UPDATE_TIME] [datetime] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[NOTE_ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[an_note_category]    Script Date: 2015/11/9 星期一 下午 1:54:11 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[an_note_category](
	[CATEGORY_ID] [int] IDENTITY(1,1) NOT NULL,
	[CATEGORY_NAME] [varchar](20) NOT NULL,
	[STATUS] [char](1) NOT NULL,
	[DELFLAG] [char](1) NOT NULL,
	[CREATE_USER] [varchar](20) NOT NULL,
	[CREATE_TIME] [datetime] NOT NULL,
	[UPDATE_USER] [varchar](20) NOT NULL,
	[UPDATE_TIME] [datetime] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[CATEGORY_ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[an_operation_history]    Script Date: 2015/11/9 星期一 下午 1:54:11 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[an_operation_history](
	[oh_id] [varchar](32) NOT NULL,
	[oh_user_name] [varchar](32) NULL,
	[oh_user_id] [varchar](32) NULL,
	[oh_operate_time] [datetime] NULL,
	[oh_operate_class] [varchar](32) NULL,
	[oh_operate_function] [varchar](32) NULL,
	[oh_lh_id] [varchar](32) NULL,
 CONSTRAINT [PK_AN_OPERATION_HISTORY] PRIMARY KEY CLUSTERED 
(
	[oh_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[an_picture]    Script Date: 2015/11/9 星期一 下午 1:54:11 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[an_picture](
	[PICTURE_ID] [int] IDENTITY(1,1) NOT NULL,
	[ALBUM_ID] [int] NOT NULL,
	[PICTURE_NAME] [varchar](255) NULL,
	[TYPE] [varchar](10) NULL,
	[LDATA] [image] NULL,
	[LPATH] [varchar](100) NULL,
	[SPATH] [varchar](100) NULL,
	[SDATA] [image] NULL,
	[STATUS] [char](1) NOT NULL,
	[DELFLAG] [char](1) NOT NULL,
	[CREATE_USER] [varchar](20) NOT NULL,
	[CREATE_TIME] [datetime] NOT NULL,
	[UPDATE_USER] [varchar](20) NOT NULL,
	[UPDATE_TIME] [datetime] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[PICTURE_ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[an_sign_in]    Script Date: 2015/11/9 星期一 下午 1:54:11 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[an_sign_in](
	[si_id] [varchar](40) NOT NULL,
	[si_sign_user_id] [varchar](20) NOT NULL,
	[si_sign_user_name] [varchar](50) NOT NULL,
	[si_date] [smalldatetime] NOT NULL,
	[si_address] [varchar](50) NOT NULL,
	[si_ip] [varchar](50) NOT NULL,
	[si_remark] [varchar](250) NULL,
 CONSTRAINT [PK_an_sign_in] PRIMARY KEY CLUSTERED 
(
	[si_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[an_todo]    Script Date: 2015/11/9 星期一 下午 1:54:11 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[an_todo](
	[TODO_ID] [int] IDENTITY(1,1) NOT NULL,
	[CATEGORY_ID] [int] NULL,
	[TODO_CONTENT] [varchar](500) NOT NULL,
	[DEAL_DATE] [datetime] NULL,
	[LEVEL] [char](1) NOT NULL,
	[STATUS] [char](1) NOT NULL,
	[DELFLAG] [char](1) NOT NULL,
	[CREATE_USER] [varchar](20) NOT NULL,
	[CREATE_TIME] [datetime] NOT NULL,
	[UPDATE_USER] [varchar](20) NOT NULL,
	[UPDATE_TIME] [datetime] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[TODO_ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[an_todo_category]    Script Date: 2015/11/9 星期一 下午 1:54:11 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[an_todo_category](
	[CATEGORY_ID] [int] IDENTITY(1,1) NOT NULL,
	[CATEGORY_NAME] [varchar](20) NOT NULL,
	[STATUS] [char](1) NOT NULL,
	[DELFLAG] [char](1) NOT NULL,
	[CREATE_USER] [varchar](20) NOT NULL,
	[CREATE_TIME] [datetime] NOT NULL,
	[UPDATE_USER] [varchar](20) NOT NULL,
	[UPDATE_TIME] [datetime] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[CATEGORY_ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[an_user]    Script Date: 2015/11/9 星期一 下午 1:54:11 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[an_user](
	[USER_ID] [varchar](20) NOT NULL,
	[USER_NAME] [varchar](20) NOT NULL,
	[PASSWORD] [varchar](32) NOT NULL,
	[ROLE] [char](1) NOT NULL,
	[SEX] [char](1) NOT NULL,
	[BIRTHDAY] [datetime] NULL,
	[EMAIL] [varchar](50) NOT NULL,
	[PHONE] [varchar](20) NULL,
	[STATUS] [char](1) NOT NULL,
	[DELFLAG] [char](1) NOT NULL,
	[CREATE_USER] [varchar](20) NOT NULL,
	[CREATE_TIME] [datetime] NOT NULL,
	[UPDATE_USER] [varchar](20) NOT NULL,
	[UPDATE_TIME] [datetime] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[USER_ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[an_user_meta]    Script Date: 2015/11/9 星期一 下午 1:54:11 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
SET ANSI_PADDING ON
GO
CREATE TABLE [dbo].[an_user_meta](
	[USER_META_ID] [int] IDENTITY(1,1) NOT NULL,
	[USER_ID] [varchar](20) NOT NULL,
	[META_KEY] [varchar](255) NOT NULL,
	[META_VALUE] [varchar](255) NULL,
	[STATUS] [char](1) NOT NULL,
	[DELFLAG] [char](1) NOT NULL,
	[CREATE_USER] [varchar](20) NOT NULL,
	[CREATE_TIME] [datetime] NOT NULL,
	[UPDATE_USER] [varchar](20) NOT NULL,
	[UPDATE_TIME] [datetime] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[USER_META_ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
SET ANSI_PADDING OFF
GO
/****** Object:  Table [dbo].[schema_version]    Script Date: 2015/11/9 星期一 下午 1:54:11 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[schema_version](
	[version_rank] [int] NOT NULL,
	[installed_rank] [int] NOT NULL,
	[version] [nvarchar](50) NOT NULL,
	[description] [nvarchar](200) NULL,
	[type] [nvarchar](20) NOT NULL,
	[script] [nvarchar](1000) NOT NULL,
	[checksum] [int] NULL,
	[installed_by] [nvarchar](100) NOT NULL,
	[installed_on] [datetime] NOT NULL,
	[execution_time] [int] NOT NULL,
	[success] [bit] NOT NULL,
 CONSTRAINT [schema_version_pk] PRIMARY KEY CLUSTERED 
(
	[version] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO
/****** Object:  Index [schema_version_ir_idx]    Script Date: 2015/11/9 星期一 下午 1:54:11 ******/
CREATE NONCLUSTERED INDEX [schema_version_ir_idx] ON [dbo].[schema_version]
(
	[installed_rank] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
/****** Object:  Index [schema_version_s_idx]    Script Date: 2015/11/9 星期一 下午 1:54:11 ******/
CREATE NONCLUSTERED INDEX [schema_version_s_idx] ON [dbo].[schema_version]
(
	[success] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
/****** Object:  Index [schema_version_vr_idx]    Script Date: 2015/11/9 星期一 下午 1:54:11 ******/
CREATE NONCLUSTERED INDEX [schema_version_vr_idx] ON [dbo].[schema_version]
(
	[version_rank] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, SORT_IN_TEMPDB = OFF, DROP_EXISTING = OFF, ONLINE = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
GO
ALTER TABLE [dbo].[an_account] ADD  DEFAULT ('0') FOR [CATEGORY_ID]
GO
ALTER TABLE [dbo].[an_account] ADD  DEFAULT ('0000-00-00') FOR [ACCOUNT_DATE]
GO
ALTER TABLE [dbo].[an_account] ADD  DEFAULT ('') FOR [ACCOUNT_TYPE]
GO
ALTER TABLE [dbo].[an_account] ADD  DEFAULT ('0.00') FOR [MONEY]
GO
ALTER TABLE [dbo].[an_account] ADD  DEFAULT (NULL) FOR [REMARK]
GO
ALTER TABLE [dbo].[an_account] ADD  DEFAULT ('1') FOR [STATUS]
GO
ALTER TABLE [dbo].[an_account] ADD  DEFAULT ('1') FOR [DELFLAG]
GO
ALTER TABLE [dbo].[an_account] ADD  DEFAULT ('') FOR [CREATE_USER]
GO
ALTER TABLE [dbo].[an_account] ADD  DEFAULT ('0000-00-00 00:00:00') FOR [CREATE_TIME]
GO
ALTER TABLE [dbo].[an_account] ADD  DEFAULT ('') FOR [UPDATE_USER]
GO
ALTER TABLE [dbo].[an_account] ADD  DEFAULT ('0000-00-00 00:00:00') FOR [UPDATE_TIME]
GO
ALTER TABLE [dbo].[an_account_book] ADD  DEFAULT ('0') FOR [ACCOUNT_BOOK_NAME]
GO
ALTER TABLE [dbo].[an_account_book] ADD  DEFAULT ('1') FOR [STATUS]
GO
ALTER TABLE [dbo].[an_account_book] ADD  DEFAULT ('1') FOR [DELFLAG]
GO
ALTER TABLE [dbo].[an_account_book] ADD  DEFAULT ('') FOR [CREATE_USER]
GO
ALTER TABLE [dbo].[an_account_book] ADD  DEFAULT ('0000-00-00 00:00:00') FOR [CREATE_TIME]
GO
ALTER TABLE [dbo].[an_account_book] ADD  DEFAULT ('') FOR [UPDATE_USER]
GO
ALTER TABLE [dbo].[an_account_book] ADD  DEFAULT ('0000-00-00 00:00:00') FOR [UPDATE_TIME]
GO
ALTER TABLE [dbo].[an_account_category] ADD  DEFAULT ('') FOR [CATEGORY_NAME]
GO
ALTER TABLE [dbo].[an_account_category] ADD  DEFAULT ('1') FOR [STATUS]
GO
ALTER TABLE [dbo].[an_account_category] ADD  DEFAULT ('1') FOR [DELFLAG]
GO
ALTER TABLE [dbo].[an_account_category] ADD  DEFAULT ('') FOR [CREATE_USER]
GO
ALTER TABLE [dbo].[an_account_category] ADD  DEFAULT ('0000-00-00 00:00:00') FOR [CREATE_TIME]
GO
ALTER TABLE [dbo].[an_account_category] ADD  DEFAULT ('') FOR [UPDATE_USER]
GO
ALTER TABLE [dbo].[an_account_category] ADD  DEFAULT ('0000-00-00 00:00:00') FOR [UPDATE_TIME]
GO
ALTER TABLE [dbo].[an_album] ADD  DEFAULT ('1') FOR [STATUS]
GO
ALTER TABLE [dbo].[an_album] ADD  DEFAULT ('1') FOR [DELFLAG]
GO
ALTER TABLE [dbo].[an_album] ADD  DEFAULT ('') FOR [CREATE_USER]
GO
ALTER TABLE [dbo].[an_album] ADD  DEFAULT ('0000-00-00 00:00:00') FOR [CREATE_TIME]
GO
ALTER TABLE [dbo].[an_album] ADD  DEFAULT ('') FOR [UPDATE_USER]
GO
ALTER TABLE [dbo].[an_album] ADD  DEFAULT ('0000-00-00 00:00:00') FOR [UPDATE_TIME]
GO
ALTER TABLE [dbo].[an_document] ADD  DEFAULT (NULL) FOR [LINK]
GO
ALTER TABLE [dbo].[an_document] ADD  DEFAULT (NULL) FOR [TYPE]
GO
ALTER TABLE [dbo].[an_document] ADD  DEFAULT (NULL) FOR [SIZE]
GO
ALTER TABLE [dbo].[an_document] ADD  DEFAULT (NULL) FOR [TAGS]
GO
ALTER TABLE [dbo].[an_document] ADD  DEFAULT ('1') FOR [STATUS]
GO
ALTER TABLE [dbo].[an_document] ADD  DEFAULT ('1') FOR [DELFLAG]
GO
ALTER TABLE [dbo].[an_document] ADD  DEFAULT ('') FOR [CREATE_USER]
GO
ALTER TABLE [dbo].[an_document] ADD  DEFAULT ('0000-00-00 00:00:00') FOR [CREATE_TIME]
GO
ALTER TABLE [dbo].[an_document] ADD  DEFAULT ('') FOR [UPDATE_USER]
GO
ALTER TABLE [dbo].[an_document] ADD  DEFAULT ('0000-00-00 00:00:00') FOR [UPDATE_TIME]
GO
ALTER TABLE [dbo].[an_feed] ADD  DEFAULT (NULL) FOR [FEED_URL]
GO
ALTER TABLE [dbo].[an_feed] ADD  DEFAULT ('0') FOR [FEED_COUNT]
GO
ALTER TABLE [dbo].[an_feed] ADD  DEFAULT ('1') FOR [STATUS]
GO
ALTER TABLE [dbo].[an_feed] ADD  DEFAULT ('1') FOR [DELFLAG]
GO
ALTER TABLE [dbo].[an_feed] ADD  DEFAULT ('') FOR [CREATE_USER]
GO
ALTER TABLE [dbo].[an_feed] ADD  DEFAULT ('0000-00-00 00:00:00') FOR [CREATE_TIME]
GO
ALTER TABLE [dbo].[an_feed] ADD  DEFAULT ('') FOR [UPDATE_USER]
GO
ALTER TABLE [dbo].[an_feed] ADD  DEFAULT ('0000-00-00 00:00:00') FOR [UPDATE_TIME]
GO
ALTER TABLE [dbo].[an_feed_favorite] ADD  DEFAULT ('') FOR [TITLE]
GO
ALTER TABLE [dbo].[an_feed_favorite] ADD  DEFAULT (NULL) FOR [LINK]
GO
ALTER TABLE [dbo].[an_feed_favorite] ADD  DEFAULT (NULL) FOR [UPDATED]
GO
ALTER TABLE [dbo].[an_feed_favorite] ADD  DEFAULT ('1') FOR [STATUS]
GO
ALTER TABLE [dbo].[an_feed_favorite] ADD  DEFAULT ('1') FOR [DELFLAG]
GO
ALTER TABLE [dbo].[an_feed_favorite] ADD  DEFAULT ('') FOR [CREATE_USER]
GO
ALTER TABLE [dbo].[an_feed_favorite] ADD  DEFAULT ('0000-00-00 00:00:00') FOR [CREATE_TIME]
GO
ALTER TABLE [dbo].[an_feed_favorite] ADD  DEFAULT ('') FOR [UPDATE_USER]
GO
ALTER TABLE [dbo].[an_feed_favorite] ADD  DEFAULT ('0000-00-00 00:00:00') FOR [UPDATE_TIME]
GO
ALTER TABLE [dbo].[an_note] ADD  DEFAULT ('0') FOR [CATEGORY_ID]
GO
ALTER TABLE [dbo].[an_note] ADD  DEFAULT ('') FOR [TITLE]
GO
ALTER TABLE [dbo].[an_note] ADD  DEFAULT ('1') FOR [STATUS]
GO
ALTER TABLE [dbo].[an_note] ADD  DEFAULT ('1') FOR [DELFLAG]
GO
ALTER TABLE [dbo].[an_note] ADD  DEFAULT ('') FOR [CREATE_USER]
GO
ALTER TABLE [dbo].[an_note] ADD  DEFAULT ('0000-00-00 00:00:00') FOR [CREATE_TIME]
GO
ALTER TABLE [dbo].[an_note] ADD  DEFAULT ('') FOR [UPDATE_USER]
GO
ALTER TABLE [dbo].[an_note] ADD  DEFAULT ('0000-00-00 00:00:00') FOR [UPDATE_TIME]
GO
ALTER TABLE [dbo].[an_note_category] ADD  DEFAULT ('') FOR [CATEGORY_NAME]
GO
ALTER TABLE [dbo].[an_note_category] ADD  DEFAULT ('1') FOR [STATUS]
GO
ALTER TABLE [dbo].[an_note_category] ADD  DEFAULT ('1') FOR [DELFLAG]
GO
ALTER TABLE [dbo].[an_note_category] ADD  DEFAULT ('') FOR [CREATE_USER]
GO
ALTER TABLE [dbo].[an_note_category] ADD  DEFAULT ('0000-00-00 00:00:00') FOR [CREATE_TIME]
GO
ALTER TABLE [dbo].[an_note_category] ADD  DEFAULT ('') FOR [UPDATE_USER]
GO
ALTER TABLE [dbo].[an_note_category] ADD  DEFAULT ('0000-00-00 00:00:00') FOR [UPDATE_TIME]
GO
ALTER TABLE [dbo].[an_picture] ADD  DEFAULT (NULL) FOR [PICTURE_NAME]
GO
ALTER TABLE [dbo].[an_picture] ADD  DEFAULT (NULL) FOR [TYPE]
GO
ALTER TABLE [dbo].[an_picture] ADD  DEFAULT (NULL) FOR [LPATH]
GO
ALTER TABLE [dbo].[an_picture] ADD  DEFAULT (NULL) FOR [SPATH]
GO
ALTER TABLE [dbo].[an_picture] ADD  DEFAULT ('1') FOR [STATUS]
GO
ALTER TABLE [dbo].[an_picture] ADD  DEFAULT ('1') FOR [DELFLAG]
GO
ALTER TABLE [dbo].[an_picture] ADD  DEFAULT ('') FOR [CREATE_USER]
GO
ALTER TABLE [dbo].[an_picture] ADD  DEFAULT ('0000-00-00 00:00:00') FOR [CREATE_TIME]
GO
ALTER TABLE [dbo].[an_picture] ADD  DEFAULT ('') FOR [UPDATE_USER]
GO
ALTER TABLE [dbo].[an_picture] ADD  DEFAULT ('0000-00-00 00:00:00') FOR [UPDATE_TIME]
GO
ALTER TABLE [dbo].[an_todo] ADD  DEFAULT (NULL) FOR [CATEGORY_ID]
GO
ALTER TABLE [dbo].[an_todo] ADD  DEFAULT ('') FOR [TODO_CONTENT]
GO
ALTER TABLE [dbo].[an_todo] ADD  DEFAULT ('0000-00-00 00:00:00') FOR [DEAL_DATE]
GO
ALTER TABLE [dbo].[an_todo] ADD  DEFAULT ('2') FOR [LEVEL]
GO
ALTER TABLE [dbo].[an_todo] ADD  DEFAULT ('1') FOR [STATUS]
GO
ALTER TABLE [dbo].[an_todo] ADD  DEFAULT ('1') FOR [DELFLAG]
GO
ALTER TABLE [dbo].[an_todo] ADD  DEFAULT ('') FOR [CREATE_USER]
GO
ALTER TABLE [dbo].[an_todo] ADD  DEFAULT ('0000-00-00 00:00:00') FOR [CREATE_TIME]
GO
ALTER TABLE [dbo].[an_todo] ADD  DEFAULT ('') FOR [UPDATE_USER]
GO
ALTER TABLE [dbo].[an_todo] ADD  DEFAULT ('0000-00-00 00:00:00') FOR [UPDATE_TIME]
GO
ALTER TABLE [dbo].[an_todo_category] ADD  DEFAULT ('') FOR [CATEGORY_NAME]
GO
ALTER TABLE [dbo].[an_todo_category] ADD  DEFAULT ('1') FOR [STATUS]
GO
ALTER TABLE [dbo].[an_todo_category] ADD  DEFAULT ('1') FOR [DELFLAG]
GO
ALTER TABLE [dbo].[an_todo_category] ADD  DEFAULT ('') FOR [CREATE_USER]
GO
ALTER TABLE [dbo].[an_todo_category] ADD  DEFAULT ('0000-00-00 00:00:00') FOR [CREATE_TIME]
GO
ALTER TABLE [dbo].[an_todo_category] ADD  DEFAULT ('') FOR [UPDATE_USER]
GO
ALTER TABLE [dbo].[an_todo_category] ADD  DEFAULT ('0000-00-00 00:00:00') FOR [UPDATE_TIME]
GO
ALTER TABLE [dbo].[an_user] ADD  DEFAULT ('') FOR [USER_ID]
GO
ALTER TABLE [dbo].[an_user] ADD  DEFAULT ('') FOR [USER_NAME]
GO
ALTER TABLE [dbo].[an_user] ADD  DEFAULT ('') FOR [PASSWORD]
GO
ALTER TABLE [dbo].[an_user] ADD  DEFAULT ('1') FOR [ROLE]
GO
ALTER TABLE [dbo].[an_user] ADD  DEFAULT ('1') FOR [SEX]
GO
ALTER TABLE [dbo].[an_user] ADD  DEFAULT (NULL) FOR [BIRTHDAY]
GO
ALTER TABLE [dbo].[an_user] ADD  DEFAULT (NULL) FOR [EMAIL]
GO
ALTER TABLE [dbo].[an_user] ADD  DEFAULT (NULL) FOR [PHONE]
GO
ALTER TABLE [dbo].[an_user] ADD  DEFAULT ('1') FOR [STATUS]
GO
ALTER TABLE [dbo].[an_user] ADD  DEFAULT ('1') FOR [DELFLAG]
GO
ALTER TABLE [dbo].[an_user] ADD  DEFAULT ('') FOR [CREATE_USER]
GO
ALTER TABLE [dbo].[an_user] ADD  DEFAULT ('0000-00-00 00:00:00') FOR [CREATE_TIME]
GO
ALTER TABLE [dbo].[an_user] ADD  DEFAULT ('') FOR [UPDATE_USER]
GO
ALTER TABLE [dbo].[an_user] ADD  DEFAULT ('0000-00-00 00:00:00') FOR [UPDATE_TIME]
GO
ALTER TABLE [dbo].[an_user_meta] ADD  DEFAULT ('') FOR [USER_ID]
GO
ALTER TABLE [dbo].[an_user_meta] ADD  DEFAULT ('') FOR [META_KEY]
GO
ALTER TABLE [dbo].[an_user_meta] ADD  DEFAULT ('') FOR [META_VALUE]
GO
ALTER TABLE [dbo].[an_user_meta] ADD  DEFAULT ('1') FOR [STATUS]
GO
ALTER TABLE [dbo].[an_user_meta] ADD  DEFAULT ('1') FOR [DELFLAG]
GO
ALTER TABLE [dbo].[an_user_meta] ADD  DEFAULT ('') FOR [CREATE_USER]
GO
ALTER TABLE [dbo].[an_user_meta] ADD  DEFAULT ('0000-00-00 00:00:00') FOR [CREATE_TIME]
GO
ALTER TABLE [dbo].[an_user_meta] ADD  DEFAULT ('') FOR [UPDATE_USER]
GO
ALTER TABLE [dbo].[an_user_meta] ADD  DEFAULT ('0000-00-00 00:00:00') FOR [UPDATE_TIME]
GO
ALTER TABLE [dbo].[schema_version] ADD  DEFAULT (getdate()) FOR [installed_on]
GO
ALTER TABLE [dbo].[an_account]  WITH CHECK ADD  CONSTRAINT [FK_ACCOUNT2BOOK] FOREIGN KEY([ACCOUNT_BOOK_ID])
REFERENCES [dbo].[an_account_book] ([ACCOUNT_BOOK_ID])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[an_account] CHECK CONSTRAINT [FK_ACCOUNT2BOOK]
GO
ALTER TABLE [dbo].[an_account]  WITH CHECK ADD  CONSTRAINT [FK_ACCOUNT2CATEGORY] FOREIGN KEY([CATEGORY_ID])
REFERENCES [dbo].[an_account_category] ([CATEGORY_ID])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[an_account] CHECK CONSTRAINT [FK_ACCOUNT2CATEGORY]
GO
ALTER TABLE [dbo].[an_note]  WITH CHECK ADD  CONSTRAINT [FK_NOTE2CATEGORY] FOREIGN KEY([CATEGORY_ID])
REFERENCES [dbo].[an_note_category] ([CATEGORY_ID])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[an_note] CHECK CONSTRAINT [FK_NOTE2CATEGORY]
GO
ALTER TABLE [dbo].[an_operation_history]  WITH CHECK ADD  CONSTRAINT [FK_AN_OPERA_REFERENCE_AN_LOGIN] FOREIGN KEY([oh_lh_id])
REFERENCES [dbo].[an_login_history] ([lh_id])
GO
ALTER TABLE [dbo].[an_operation_history] CHECK CONSTRAINT [FK_AN_OPERA_REFERENCE_AN_LOGIN]
GO
ALTER TABLE [dbo].[an_picture]  WITH CHECK ADD  CONSTRAINT [FK_PICTURE2ALBUM] FOREIGN KEY([ALBUM_ID])
REFERENCES [dbo].[an_album] ([ALBUM_ID])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[an_picture] CHECK CONSTRAINT [FK_PICTURE2ALBUM]
GO
ALTER TABLE [dbo].[an_sign_in]  WITH CHECK ADD  CONSTRAINT [sign_in_user_FK] FOREIGN KEY([si_sign_user_id])
REFERENCES [dbo].[an_user] ([USER_ID])
GO
ALTER TABLE [dbo].[an_sign_in] CHECK CONSTRAINT [sign_in_user_FK]
GO
ALTER TABLE [dbo].[an_user_meta]  WITH CHECK ADD  CONSTRAINT [FK_USERMETA2USER] FOREIGN KEY([USER_ID])
REFERENCES [dbo].[an_user] ([USER_ID])
ON UPDATE CASCADE
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[an_user_meta] CHECK CONSTRAINT [FK_USERMETA2USER]
GO
USE [master]
GO
ALTER DATABASE [Anynote] SET  READ_WRITE 
GO
