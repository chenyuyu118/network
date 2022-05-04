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
    file = meg.split()[1]
    file = "." + file.decode()
    print("file: " + file)
    try:
        byteArray = open(file, "r").read()
        retMeg = "HTTP/1.0 200 OK\r\nConnection: close\r\nServer:Apache/2.2.3(CentOS)\r\
        nLast-Modified:Tue, 18 Aug 2015 15:11:03 GMT\r\nContent-Length:" + str(len(byteArray)) + "\r\n\n"
        connetSocket.send(retMeg.encode())
        for i in range(0, len(byteArray)):
            connetSocket.send(byteArray[i].encode())
    except IOError:
        retMeg = "HTTP/1.0 404 Found\r\n"
        connetSocket.send(retMeg.encode())
    finally:
        connetSocket.close()