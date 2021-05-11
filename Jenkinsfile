def title(cmd) {
    sh('#!/bin/sh -e\necho -e "\\e[1m\\e[34m' + cmd + '\\e[39m\\e[0m"')
}

def warn(cmd) {
    sh('#!/bin/sh -e\necho -e "\\e[1m\\e[35m' + cmd + '\\e[39m\\e[0m"')
}

def result(cmd) {
    sh('#!/bin/sh -e\necho -e "\\e[1m\\e[32m' + cmd + '\\e[39m\\e[0m"')
}

properties([
  pipelineTriggers([
   [$class: 'GenericTrigger',
    genericVariables:[
     [
      key: 'refsb',
      value: '$.changes[0].ref.id',
      expressionType: 'JSONPath', 
      regexpFilter: '^(refs/heads/|refs/remotes/origin/)'
     ],
     [
      key: 'refsb',
      value: '$.pullRequest.toRef.id',
      expressionType: 'JSONPath', 
      regexpFilter: '^(refs/heads/|refs/remotes/origin/)'
     ]
	 ],   
    causeString: 'Triggered on $refsb',
    
    token: '',
    
    printContributedVariables: true,
    printPostContent: true,
    
    silentResponse: false,
    
    regexpFilterText: '$refsb',
    regexpFilterExpression: '^(master)*?$'
   ]
  ])
 ])


pipeline {

    environment {
        ROLEARN = ""   
        ProjectArn : ""
        jenkinsCredentialsForBitbucket = "BitBucket-Admin"
        repository = ""
        REGION = "us-east-1"
        
    }

 

    agent { label 'fargate-prod-jenkins-slave-nna'}

    stages {
        stage("Git Checkout") {
            steps {
                title "Clone"
                script {
                    def scmVars = checkout([$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false, extensions: [[$class: 'CloneOption', noTags: false, reference: '', shallow: true]], submoduleCfg: [], userRemoteConfigs: [[credentialsId: jenkinsCredentialsForBitbucket, url: repository]]])
                    env.GIT_COMM = scmVars.GIT_COMMIT.substring(0, 8)
                }
            }
        }
      

        stage('Jenkins Shared Repo Checkout') {
            steps {
               echo 'Checking out shared scripts from Bit Bucket...'  
               dir('jenkins-libraries') {
                    git  branch: "master", url: "'
                }
            }
          }


        stage ("MVN Build") {
            steps{
                echo '***** Lets grab the properties *****'
                script {
                    
                    sh 'mvn clean compile'
                        }
                    echo '**************** END - Maven pipeline ****************'
                }                       
            } 

		stage('MVN Test') {
            steps {
                echo '**************** START - Maven pipeline ****************'
                
                  sh 'mvn test'
                }
				echo '**************** END - Maven pipeline ****************'
                }
           }
    
       
      

                    stage("Docker Image") {
                        steps {
                            title "Build and tag the Docker Image"
                            script {
                                // docker.build(imageName, "--build-arg http_proxy=${http_proxy_ne} --build-arg https_proxy=${https_proxy_ne} --build-arg no_proxy=${no_proxy_ne} -f Dockerfile .")

                                //docker.build(imageName, "-f Dockerfile ./")
                                dir("./") {
                                sh "docker build -t ${imageName} -f Dockerfile ."
                                }
                                
                            }
                        }
                    }

  
