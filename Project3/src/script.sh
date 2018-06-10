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
tar -czf registry.tar.gz registry/
tar -czf GeneralRepository.tar.gz monitors/GeneralRepository/
tar -czf Paddock.tar.gz monitors/Paddock/
tar -czf Stable.tar.gz monitors/Stable/
tar -czf BettingCentre.tar.gz monitors/BettingCentre/
tar -czf RacingTrack.tar.gz monitors/RacingTrack/
tar -czf ControlCentre.tar.gz monitors/ControlCentre/
tar -czf main.tar.gz main/
tar -czf interfaces.tar.gz interfaces/
tar -czf states.tar.gz states/

sleep 5

echo "Unziping code in machines"
sshpass -p $password scp registry.tar.gz $username@$registryHostName:~/Public/
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$registryHostName "cd ~/Public/ ; tar -xmzf registry.tar.gz" &

sshpass -p $password scp GeneralRepository.tar.gz $username@$GeneralRepositoryHostName:~/Public/
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$GeneralRepositoryHostName "cd ~/Public/ ; tar -xmzf GeneralRepository.tar.gz; tar -xmzf interfaces.tar.gz; tar -xmzf states.tar.gz" &

sshpass -p $password scp Paddock.tar.gz $username@$PaddockHostName:~/Public/
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$PaddockHostName "cd ~/Public/ ; tar -xmzf Paddock.tar.gz; tar -xmzf interfaces.tar.gz; tar -xmzf states.tar.gz" &

sshpass -p $password scp Stable.tar.gz $username@$StableHostName:~/Public/
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$StableHostName "cd ~/Public/ ; tar -xmzf Stable.tar.gz; tar -xmzf interfaces.tar.gz; tar -xmzf states.tar.gz" &

sshpass -p $password scp BettingCentre.tar.gz $username@$BettingCenterHostName:~/Public/
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$BettingCenterHostName "cd ~/Public/ ; tar -xmzf BettingCentre.tar.gz; tar -xmzf interfaces.tar.gz; tar -xmzf states.tar.gz" &

sshpass -p $password scp RacingTrack.tar.gz $username@$RaceTrackHostName:~/Public/
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$RaceTrackHostName "cd ~/Public/ ; tar -xmzf RacingTrack.tar.gz; tar -xmzf interfaces.tar.gz; tar -xmzf states.tar.gz" &

sshpass -p $password scp ControlCentre.tar.gz $username@$ControlCenterHostName:~/Public/
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$ControlCenterHostName "cd ~/Public/ ; tar -xmzf ControlCentre.tar.gz; tar -xmzf interfaces.tar.gz; tar -xmzf states.tar.gz" &

sshpass -p $password scp dir_MasterThief.tar.gz $username@$BrokerHostName:~/Public/
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$BrokerHostName "cd ~/Public/ ; tar -xmzf main.tar.gz; tar -xmzf interfaces.tar.gz; tar -xmzf states.tar.gz" &

sshpass -p $password scp dir_MasterThief.tar.gz $username@$HorsesHostName:~/Public/
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$HorsesHostName "cd ~/Public/ ; tar -xmzf main.tar.gz; tar -xmzf interfaces.tar.gz; tar -xmzf states.tar.gz" &

sshpass -p $password scp dir_MasterThief.tar.gz $username@$SpectatorHostName:~/Public/
sshpass -p $password ssh -o StrictHostKeyChecking=no -f $username@$SpectatorHostName "cd ~/Public/ ; tar -xmzf main.tar.gz; tar -xmzf interfaces.tar.gz; tar -xmzf states.tar.gz" &


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