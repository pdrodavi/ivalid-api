library identifier: 'jenkins-core-library@kubernetes/1.0.0', retriever: modernSCM(
  [$class: 'GitSCMSource',
    remote: 'https://github.com/pdrodavi/jenkins-core-library.git'
  ])

pipeline {
    
  agent {
    kubernetes {
      yamlFile 'docker-maven-jnlp-pod-template.yaml'
    }
  }
  
  tools {
    maven "M3"
  }  
  
  stages {

    stage('Checkout') {
      steps {
        container('maven') {
          git branch: 'release/1.0.0', changelog: false, poll: false, url: 'https://github.com/pdrodavi/ivalid-api.git'
        }
      }
    }
      
    stage('Analysis') {
      steps {
          script {
            inputAnalysis = input([
                    message: 'Analysis SonarQube?',
                    parameters: [
                            choice(name: 'Analysis', choices: ['Yes', 'No'], description: 'Run on specific analysis')
                    ]
            ])

            Boolean executeStage = false

            if ("${inputAnalysis}" == 'Yes') {
                executeStage = true
            }

            conditionalStage("Analysis SonarQube", executeStage) {

                if ("${inputAnalysis}" == 'Yes') {
                    withSonarQubeEnv('sonarqube') {
                        sh "mvn -B clean verify sonar:sonar"
                    }
                    def qualitygate = waitForQualityGate()
                    if (qualitygate.status != "OK") {
                        cleanWs()
                        error "Pipeline aborted due to quality gate failure: ${qualitygate.status}"
                    }
                } else {
                    println("Step Skipped")
                }
            }
          }
      }
    }  

    stage('Package') {
      steps {
          script {
            println("Realizando construção do artefato")
            println("Artifact: " + readMavenPom().getArtifactId())
            println("Version: " + readMavenPom().getVersion())
            sh "mvn -Dmaven.test.skip=true -Dmaven.test.failure.ignore package"
          }
      }
    }

    stage('Build Image') {
      steps {
        container('docker') {
          println("Criando a imagem Docker")
          sh "docker build -t ${readMavenPom().getArtifactId()}:${readMavenPom().getVersion()} ."
        }
      }
    }
  }
}
