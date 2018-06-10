#!/usr/bin/env bash
username=sd0107
password=grupomaisfixedesempre96
url=http://192.168.8.171/sd0407/classes/
registryHostName=l040101-ws01.ua.pt
GeneralRepositoryHostName=l040101-ws02.ua.pt
ControlCenterHostName=l040101-ws03.ua.pt
BettingCenterHostName=l040101-ws04.ua.pt
RaceTrackHostName=l040101-ws05.ua.pt
StableHostName=l040101-ws06.ua.pt
PaddockHostName=l040101-ws07.ua.pt
BrokerHostName=l040101-ws08.ua.pt
HorsesHostName=l040101-ws09.ua.pt
SpectatorHostName=l040101-ws10.ua.pt
registryPortNum=22460

echo "compressing source code..."

tar -czf code.tar.gz registry/ monitors/ main/ interfaces/ states/

sleep 5

echo "Unziping code in machines"
sshpass -p $password scp code.tar.gz $username@$registryHostName:~/Public/
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$registryHostName "cd ~/Public/ ; tar -xmzf code.tar.gz" &

sshpass -p $password scp code.tar.gz $username@$GeneralRepositoryHostName:~/Public/
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$GeneralRepositoryHostName "cd ~/Public/ ; tar -xmzf code.tar.gz;" &

sshpass -p $password scp code.tar.gz $username@$PaddockHostName:~/Public/
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$PaddockHostName "cd ~/Public/ ; tar -xmzf code.tar.gz; " &

sshpass -p $password scp code.tar.gz $username@$StableHostName:~/Public/
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$StableHostName "cd ~/Public/ ; tar -xmzf code.tar.gz; " &

sshpass -p $password scp code.tar.gz $username@$BettingCenterHostName:~/Public/
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$BettingCenterHostName "cd ~/Public/ ; tar -xmzf code.tar.gz; " &

sshpass -p $password scp code.tar.gz $username@$RaceTrackHostName:~/Public/
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$RaceTrackHostName "cd ~/Public/ ; tar -xmzf code.tar.gz; " &

sshpass -p $password scp code.tar.gz $username@$ControlCenterHostName:~/Public/
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$ControlCenterHostName "cd ~/Public/ ; tar -xmzf code.tar.gz; " &

sshpass -p $password scp code.tar.gz $username@$BrokerHostName:~/Public/
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$BrokerHostName "cd ~/Public/ ; tar -xmzf code.tar.gz; " &

sshpass -p $password scp code.tar.gz $username@$HorsesHostName:~/Public/
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$HorsesHostName "cd ~/Public/ ; tar -xmzf code.tar.gz; " &

sshpass -p $password scp code.tar.gz $username@$SpectatorHostName:~/Public/
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$SpectatorHostName "cd ~/Public/ ; tar -xmzf code.tar.gz; " &


echo "Setting RMI repository.... "
echo " "
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$registryHostName "cd ~/Public/ ; rmiregistry -J-Djava.rmi.server.useCodebaseOnly=false 5000;" &
sleep 2
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$registryHostName "cd ~/Public/ ; java 
-Djava.rmi.server.codebase="$(pwd)"\
     -Djava.rmi.server.useCodebaseOnly=false\
     -Djava.security.policy=java.policy\
     registry.ServerRegisterRemoteObject" &
sleep 5
echo " "


echo "Setting General Repository.... "
echo " "
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$GeneralRepositoryHostName "cd ~/Public/ ; java monitors/GeneralRepository/GeneralRepositoryStart 22170 localhost 5000" &
sleep 5
echo " "


echo "Setting Control Center.... "
echo " "
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$ControlCenterHostName "cd ~/Public/ ; java monitors/ControlCentre/ControlCentreStart 22174 localhost 5000" &
sleep 5
echo " "


echo "Setting Betting Center.... "
echo " "
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$BettingCenterHostName "cd ~/Public/ ; java monitors/BettingCentre/BettingCentreStart 22173 localhost 5000" &
sleep 5
echo " "


echo "Setting RaceTrack.... "
echo " "
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$RaceTrackHostName "cd ~/Public/ ; java monitors/RacingTrack/RacingTrackStart 22175 localhost 5000" &
sleep 5
echo " "


echo "Setting Stable.... "
echo " "
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$StableHostName "cd ~/Public/ ; java monitors/Stable/StableStart 22171 localhost 5000" &
sleep 5
echo " "


echo "Setting Paddock.... "
echo " "
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$PaddockHostName "cd ~/Public/ ; java monitors/Paddock/PaddockStart 22172 localhost 5000" &
sleep 5
echo " "


echo "Setting Broker.... "
echo " "
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$BrokerHostName "cd ~/Public/ ; java monitors/main/BrokerStart 22179 localhost 5000" &
sleep 5
echo " "

echo "Setting Horses.... "
echo " "
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$HorsesHostName "cd ~/Public/ ; java monitors/main/HorseStart 22178 localhost 5000" &
sleep 5
echo " "

echo "Setting Spectator.... "
echo " "
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$SpectatorHostName "cd ~/Public/ ; java monitors/main/SpectatorStart 22177 localhost 5000" &
sleep 5
echo " "

sh clean_class_zip.sh
echo " "
