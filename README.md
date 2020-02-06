# Calls Service Backend

# PARA INSTALAR E CORRER

Primeiro lugar é necessário ter o Postgres Instalado na Máquina onde se vai correr a aplicação. Basta instalar a versão mais recente aqui https://www.enterprisedb.com/downloads/postgres-postgresql-downloads encontrada!

A aplicação como é desenvolvida em Spring Boot contém um servidor aplicacional Tomcat que fica disponivel na porta 8080. Toda essa ligação com o Front-End está desenvolvida pelo que não tem de haver preocupação com isso

Dentro da pasta resources no projecto podemos encontrar um ficheiro application.properties que contém as configs de ligação com a BD

# spring.datasource.username : postgres
# spring.datasource.password : admin
# spring.datasource.url : calls_db

Estas definições podem ser alteradas consoante os dados utilizados para criar a BD em Postgres. Fica ao critério de quem for testar!

# Eu usei IntelliJ como IDE para desenvolver mas penso que usando outros IDE's não devem surgir problemas, por isso deixei ficar desta forma

Os testes realizados são apenas testes unitários em JUnit e com a ajuda do Mockito para fazer Mocks dos Repositorios

Todos os testes se encontram a passar e cobrem grande parte dos serviços da aplicação!
