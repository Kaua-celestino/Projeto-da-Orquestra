# Sistema de Gestão de Orquestra

## O que precisa para rodar
* **SDK:** **BellSoft Liberica Full JDK 17** (ou **21**) — precisa ser obrigatoriamente a versão **Full**, pois já inclui o JavaFX.
* **Biblioteca Externa:** Arquivo `.jar` de geração de PDF (iText / OpenPDF) vinculado ao projeto.
* **IDE:** IntelliJ IDEA.

## Como executar
1. Abra o projeto no IntelliJ e configure o SDK em `File > Project Structure > Project` (**Liberica Full JDK 17**).
2. Ainda em `Project Structure`, vá em `Modules > Dependencies`, clique em **`+` > `JARs or Directories...`** e selecione o `.jar` da biblioteca de PDF.
3. No menu superior, clique em **`Build > Rebuild Project`** (necessário para carregar as telas `.fxml` e imagens).
4. Execute o arquivo **`gestao_orquestra/App.java`**.

## Login Inicial
* **Usuário padrão:** `admin` | **Senha:** `1234` (gerado automaticamente na primeira execução).
* Também é possível criar novos usuários direto na tela de login em **"Criar uma nova conta"**.
