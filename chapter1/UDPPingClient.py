from ast import Bytes
from socket import *
from datetime import *
from time import *
from timeit import Timer

client = socket(AF_INET, SOCK_DGRAM)
client.settimeout(1)

# host = "localhost"
host = "175.178.166.84"

i = 0
while i != 10:
    start = time()
    client.sendto("ping message".encode(), (host, 12000))
    try:
        data = client.recvfrom(2048)
        print("pass time:%.5f s" % (time()-start))
        print(data[0].decode()) 
    except Exception as e:
        print("Time Out!")
    finally:
        i = i + 1
