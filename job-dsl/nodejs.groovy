job('NodeJS_dsl_test01') {
    scm {
        git('git://github.com/ashishmathai/nodejssample.git') {  node -> // is hudson.plugins.git.GitSCM
            node / gitConfigName('DSL User')
            node / gitConfigEmail('test@test.com')
        }
    }
    triggers {
        scm('* * * * *')
    }
    wrappers {
        nodejs('nodejs') // this is the name of the NodeJS installation in
                         // Manage Jenkins -> Configure Tools -> NodeJS Installations -> Name
    }
    steps {
        shell '''

echo "Hello, Welcome to DSL script Jenkins Job"
sshpass -p 'ashish' ssh -o StrictHostKeyChecking=no ashish@192.168.0.112 "ls /opt/NodeProject/ > /tmp/name"
x=`sshpass -p 'ashish' ssh -o StrictHostKeyChecking=no ashish@192.168.0.112 "cat /tmp/name"`
if [[ $x != "nodejssample" ]]
then
        echo "EXEC IF"
        sshpass -p 'ashish' ssh -o StrictHostKeyChecking=no ashish@192.168.0.112 "cd /opt/NodeProject; git clone https://github.com/ashishmathai/nodejssample.git; cd /opt/NodeProject/nodejssample/; npm install; node index.js &"
        echo "---------------------"
        echo "Verify Node running index.js"
        sshpass -p 'ashish' ssh -o StrictHostKeyChecking=no ashish@192.168.0.112 "sudo ps aux | grep -i index.js| grep -v grep"
        echo "---------------------"
        echo "Local Curl Test"
        sshpass -p 'ashish' ssh -o StrictHostKeyChecking=no ashish@192.168.0.112 "curl localhost:3000; curl -I localhost:3000"
        echo "---------------------"
        echo "Remote Curl Test"
        curl 192.168.0.112:3000
        curl -I 192.168.0.112:3000
        echo "---------------------"
else
        echo "EXEC ELSE"
        sshpass -p 'ashish' ssh -o StrictHostKeyChecking=no ashish@192.168.0.112 "cd /opt/NodeProject/nodejssample/; git pull https://github.com/ashishmathai/nodejssample.git; cd /opt/NodeProject/nodejssample/; npm install; node index.js &"
        echo "---------------------"
        echo "Verify Node running index.js"
        sshpass -p 'ashish' ssh -o StrictHostKeyChecking=no ashish@192.168.0.112 "sudo ps aux | grep -i index.js| grep -v grep"
        echo "---------------------"
        echo "Local Curl Test"
        sshpass -p 'ashish' ssh -o StrictHostKeyChecking=no ashish@192.168.0.112 "curl localhost:3000; curl -I localhost:3000"
        echo "---------------------"
fi
echo "Remote Curl Test"
curl 192.168.0.112:3000
curl -I 192.168.0.112:3000
echo "---------------------"

        '''
    }
}
