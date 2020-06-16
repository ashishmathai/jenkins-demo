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
        shell("echo 'Hello, Welcome to DSL script Jenkins Job'")
	shell('sshpass -p "ashish" ssh -o StrictHostKeyChecking=no ashish@192.168.0.112 "date; date >> /tmp/date; sudo mkdir -p /opt/NodeProject/"')
	shell('sshpass -p "ashish" ssh -o StrictHostKeyChecking=no ashish@192.168.0.112 "cd /opt/NodeProject; git clone https://github.com/ashishmathai/nodejssample.git"')
	shell('sshpass -p "ashish" ssh -o StrictHostKeyChecking=no ashish@192.168.0.112 "cd /opt/NodeProject/nodejssample/; npm install"')
	shell('sshpass -p "ashish" ssh -o StrictHostKeyChecking=no ashish@192.168.0.112 "sudo chown -R ashish:ashish /opt/NodeProject; cd /opt/NodeProject/nodejssample; node index.js &"')
	shell('echo "Remote Curl Test"')
	shell('curl 192.168.0.112:3000; curl -I 192.168.0.112:3000')
	shell('echo "---------------------"')
    }
}
