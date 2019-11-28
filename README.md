## Tecnologias:
- Java 8
- Wildfly 17
- JSF 2.3
- Primefaces 7.0
- EJB
- Hibernate
- Postgres
- JMS

## Configuração:

### Configuração o standalone:
- standalone: https://github.com/henriquesfreitas/jsf-primefaces-ejb-wildfly-financeiro/blob/master/standalone.xml. Caso queira utilizar um standalone mais atualizado do wildfly, basta pegar o standalone-full.xml, já que esse é o que suporta fila. As configurações para modificar são os que tratam da conexão do banco e o drive <datasource jndi-name="java:jboss/financeiroDS"> e <drivers>, além das filas com nome MovimentacaoQueue.

### Instalando Postgres no Wildfly:
- Copiar pasta postgres para dentro do repositório: wildfly-17.0.1.Final\modules\system\layers\base\org
- Opcional: Baixar uma versão mais recente do jdbc do postgres: https://jdbc.postgresql.org/download.html, substituir pelo jar dentro da pasta main, depois editar o module.xml com a nova versão do arquivo

### Adicionando uma nova fila:
- Adicionar a nova fila no standalone:
	<queue name="MovimentacaoQueue"
		address="java:/jms/queue/MovimentacaoQueue" durable="true" />
	<jms-queue name="MovimentacaoQueue"
		entries="java:/jms/queue/MovimentacaoQueue" />
- Para ver a utilização, basta ver o exemplo do código
