from socket import AF_INET, SOCK_STREAM, socket


server_port = 12000

serverSocket = socket(AF_INET, SOCK_STREAM)
serverSocket.bind(('', server_port))
serverSocket.listen(1)
print("Server start to listening")
while True:
    connetSocket, ipAdd = serverSocket.accept()
    meg = connetSocket.recv(2048)
    print("meg from " + str(ipAdd[0]) + ":" + str(ipAdd[1]) + ": " + meg.decode())
    retMeg = "HTTP/1.1 200 OK\r\nConnection: close\r\nServer:Apache/2.2.3(CentOS)\r\
        nLast-Modified:Tue, 18 Aug 2015 15:11:03 GMT\r\nContent-Length:"
    connetSocket.send(retMeg.encode())
    connetSocket.close()