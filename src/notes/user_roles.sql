USE [VMDATA]
GO

IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[fk5]') AND parent_object_id = OBJECT_ID(N'[dbo].[user_roles]'))
ALTER TABLE [dbo].[user_roles] DROP CONSTRAINT [fk5]
GO

IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[fk6]') AND parent_object_id = OBJECT_ID(N'[dbo].[user_roles]'))
ALTER TABLE [dbo].[user_roles] DROP CONSTRAINT [fk6]
GO

USE [VMDATA]
GO

/****** Object:  Table [dbo].[user_roles]    Script Date: 09/16/2014 08:46:12 ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[user_roles]') AND type in (N'U'))
DROP TABLE [dbo].[user_roles]
GO


USE [VMDATA]
GO

IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[fk5]') AND parent_object_id = OBJECT_ID(N'[dbo].[user_roles]'))
ALTER TABLE [dbo].[user_roles] DROP CONSTRAINT [fk5]
GO

IF  EXISTS (SELECT * FROM sys.foreign_keys WHERE object_id = OBJECT_ID(N'[dbo].[fk6]') AND parent_object_id = OBJECT_ID(N'[dbo].[user_roles]'))
ALTER TABLE [dbo].[user_roles] DROP CONSTRAINT [fk6]
GO

USE [VMDATA]
GO

/****** Object:  Table [dbo].[user_roles]    Script Date: 09/16/2014 08:44:41 ******/
IF  EXISTS (SELECT * FROM sys.objects WHERE object_id = OBJECT_ID(N'[dbo].[user_roles]') AND type in (N'U'))
DROP TABLE [dbo].[user_roles]
GO

USE [VMDATA]
GO

/****** Object:  Table [dbo].[user_roles]    Script Date: 09/16/2014 08:44:41 ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[user_roles](
	[id] [bigint] IDENTITY(1,1) NOT NULL,
	[user_id] [bigint] NULL,
	[role_id] [int] NULL,
	[date_created] [datetime] NULL,
	[date_updated] [datetime] NULL,
	[created_by] [varchar](50) NULL,
	[updated_by] [varchar](50) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX  = OFF, STATISTICS_NORECOMPUTE  = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS  = ON, ALLOW_PAGE_LOCKS  = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

ALTER TABLE [dbo].[user_roles]  WITH CHECK ADD  CONSTRAINT [fk5] FOREIGN KEY([role_id])
REFERENCES [dbo].[roles] ([id]) ON DELETE CASCADE
GO

ALTER TABLE [dbo].[user_roles] CHECK CONSTRAINT [fk5]
GO

ALTER TABLE [dbo].[user_roles]  WITH CHECK ADD  CONSTRAINT [fk6] FOREIGN KEY([user_id])
REFERENCES [dbo].[users] ([id]) ON DELETE CASCADE
GO

ALTER TABLE [dbo].[user_roles] CHECK CONSTRAINT [fk6]
GO


