#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <stdio.h>
#include <string.h>

struct sockaddr_in serverAddr;
socklen_t addrLen = sizeof(serverAddr);
int udpFd;
int numbytes;

#define SERVER_PORT 3333

int main(int argc, char *argv[])
{


	if (argc != 3)
	{
		fprintf(stderr, "Invalid number of parameters\n");
		return 1;
	}
	
	if (inet_pton(AF_INET, argv[1], &(serverAddr.sin_addr)) != 1)
	{
		fprintf(stderr, "Invalid address\n");
		return 1;
	}
	serverAddr.sin_family = AF_INET;
	serverAddr.sin_port = htons(SERVER_PORT);

	if ((udpFd = socket(AF_INET, SOCK_DGRAM, 0)) == -1)
	{
		perror("client: socket");
		return 2;
	}

	if ((numbytes = sendto(udpFd, argv[2], strlen(argv[2]), 0, (struct sockaddr*)&serverAddr, addrLen)) == -1)
	{
		perror("client: sendto");
		return 1;
	}

	printf("client: sent %d bytes to %s\n", numbytes, argv[1]);
	close(udpFd);

	return 0;
}
