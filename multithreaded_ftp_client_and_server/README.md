#Programming Project 2: Multithreaded FTP Client and Server

The aim of this project is to introduce to you the design issues involved in multi-threaded servers. In this project, you are going to extend your first project to make both the client and server multithreaded. The client will be able to handle multiple commands (from same user) simultaneously and server should be able to handle multiple commands from multiple client. The overall interaction between the user and the system is similar except as indicated below. The client and server will support the same set of commands as indicated in project 1 (get, put, delete, ls, cd, mkdir, pwd, quit). In addition, they will support one more command called “terminate”, which is used for terminating a long-running command (e.g., get and put on large files) from the same client.

The syntax of the terminate command is as follows:
`terminate <command-ID> -- terminate the command identified by
<command-ID> (see below for a description of command ID).`

The workings of the client and server are described below:
