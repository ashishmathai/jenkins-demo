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
	remoteShell('sshpass -p "ashish" ssh ashish@192.168.0.112') {
		command('chmod +x cd /opt/NodeProject/nodejssample/script.sh')
		command('bash cd /opt/NodeProject/nodejssample/script.sh')
        }
    }
}
