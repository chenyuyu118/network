from socket import AF_INET, SOCK_STREAM, SocketIO, socket


# server_name = 'localhost'
server_name = '175.178.166.84'
server_port = 12000
socket1 = socket(AF_INET, SOCK_STREAM)
meg = input("please input lowercase sentence: ")
socket1.connect((server_name, server_port))
socket1.send(meg.encode())
modifiedMeg = socket1.recv(2048)
print("receive from server: ", modifiedMeg.decode())
socket1.close()
