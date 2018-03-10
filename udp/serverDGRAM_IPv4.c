#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <stdio.h>
#include <string.h>

#define true 1
#define MAXBUFLEN 4096
#define SERVER_PORT 3333

struct sockaddr_in udpFdAddress;
int numbytes;
struct sockaddr_in clientAddr;
socklen_t addrLen = sizeof(clientAddr);
char buf[MAXBUFLEN];
char s[INET_ADDRSTRLEN];

int initUdpSocket()
{
         socket(AF_INET, SOCK_DGRAM, 0);
}

int listenPackages(int udpFd, int argc, char** argv)
{

	// If the argument is one bind to all IP addresses of the server
	if(argc == 1)	
	{
		udpFdAddress.sin_family = AF_INET;
		udpFdAddress.sin_addr.s_addr = INADDR_ANY;
		udpFdAddress.sin_port = htons(SERVER_PORT);
	}
	else 
	{
		if (inet_pton(AF_INET, argv[1], &(udpFdAddress.sin_addr)) != 1) 
		{
			fprintf(stderr, "Invalid address\n");
			return 1;
		}
		udpFdAddress.sin_family = AF_INET;
		udpFdAddress.sin_port = htons(SERVER_PORT);
		
		char ipinput[INET_ADDRSTRLEN];
		inet_ntop(AF_INET, &(udpFdAddress.sin_addr), ipinput, INET_ADDRSTRLEN);
		printf("IP Address = %s\n", ipinput);

	}
	if(bind(udpFd, (struct sockaddr *)&udpFdAddress, sizeof udpFdAddress) == -1)
	{
		perror("bind");
		return 1;
	}
	while(true)
	{
		if ((numbytes = recvfrom(udpFd, buf, MAXBUFLEN - 1, 0, (struct sockaddr *)&clientAddr, &addrLen)) == -1) 
		{
			perror("recvfrom");
			return 1;
		}

		printf("server: got packet from %s\n",
		inet_ntop(clientAddr.sin_family,
		&clientAddr.sin_addr, s, sizeof s));
		printf("server: packet is %d bytes long\n", numbytes);
		buf[numbytes] = '\0';
		printf("server: packet contains \"%s\"\n", buf);
	}
	close(udpFd);
	return 0;
}


int main(int argc, char *argv[])
{
	int udpFd = initUdpSocket();

	if(udpFd == -1)
	{
		perror("Could not create socket");
		return 1;
	}
	
	memset(&udpFdAddress, 0, sizeof(udpFdAddress));

	return listenPackages(udpFd, argc, argv);
}
