echo "-->Compilar todo o codigo (Makefile)"
make;
echo "" 
echo "-->Enviar .class para Public/classes RMI - ws07.ua.pt" 
sshpass -p grupomaisfixedesempre96 scp -r __bin/Interfaces __bin/ClientSide __bin/Structures sd0107@l040101-ws07.ua.pt:~/Public/classes

echo "-->Enviar .class para Registry - ws07.ua.pt"
sshpass -p grupomaisfixedesempre96 scp -r  __bin/Interfaces __bin/Registry __bin/Structures sd0107@l040101-ws07.ua.pt:~/dir_registry

echo "-->Enviar .class para GeneralRepository - ws01.ua.pt "
sshpass -p grupomaisfixedesempre96 scp -r  __bin/Interfaces __bin/ServerSide __bin/Structures sd0107@l040101-ws01.ua.pt:~/dir_serverSide

echo "-->Enviar .class para Site - ws02.ua.pt"
sshpass -p grupomaisfixedesempre96 scp -r  __bin/Interfaces __bin/ServerSide __bin/Structures sd0107@l040101-ws02.ua.pt:~/dir_serverSide

echo "-->Enviar .class para Playground - ws03.ua.pt"
sshpass -p grupomaisfixedesempre96 scp -r  __bin/Interfaces __bin/ServerSide __bin/Structures sd0107@l040101-ws03.ua.pt:~/dir_serverSide

echo "-->Enviar .class para Repository - ws04.ua.pt"

sshpass -p grupomaisfixedesempre96 scp -r  __bin/Interfaces __bin/ServerSide __bin/Structures sd0107@l040101-ws04.ua.pt:~/dir_serverSide

echo "-->Enviar .class para Coach - ws05.ua.pt "

sshpass -p grupomaisfixedesempre96 scp -r  __bin/Interfaces __bin/ClientSide __bin/Structures sd0107@l040101-ws05.ua.pt:~/dir_clientSide

echo "-->Enviar .class para Contestant - ws06.ua.pt "

sshpass -p grupomaisfixedesempre96 scp -r  __bin/Interfaces __bin/ClientSide __bin/Structures sd0107@l040101-ws06.ua.pt:~/dir_clientSide

echo "-->Enviar .class para Referee - ws08.ua.pt"

sshpass -p grupomaisfixedesempre96 scp -r  __bin/Interfaces __bin/ClientSide __bin/Structures sd0107@l040101-ws08.ua.pt:~/dir_clientSide