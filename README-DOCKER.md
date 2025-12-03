# Arquitetura de Containerização (Java)

Este documento explica as decisões técnicas adotadas no `Dockerfile` para garantir segurança e performance durante o Hackathon.

## Decisões Técnicas

### 1. Multi-Stage Build
Utilizamos duas fases no processo de build:
* **Fase 1 (Builder):** Usa uma imagem pesada com Maven e JDK para compilar o código.
* **Fase 2 (Runtime):** Usa uma imagem leve (JRE) apenas para execução.
* **Resultado:** A imagem final contém apenas o binário compilado, reduzindo o tamanho de ~800MB para ~170MB e removendo o código-fonte e ferramentas de compilação do ambiente de produção.

### 2. Estratégia de Cache (Layer Caching)
Copiamos o `pom.xml` e rodamos `mvn dependency:go-offline` **antes** de copiar o código-fonte.
* **Motivo:** O Docker armazena as dependências em cache. Builds subsequentes onde apenas o código mudou (mas não as bibliotecas) serão extremamente rápidos, pulando o download da internet.

### 3. Segurança (Non-Root)
Criamos e utilizamos um usuário restrito (`appuser`) para executar a aplicação.
* **Motivo:** Impede que, em caso de invasão da aplicação (RCE), o atacante tenha privilégios de superusuário (`root`) no container.

### 4. Escolha da Base Image: Alpine vs Distroless
Optamos por usar **Alpine Linux** (`eclipse-temurin:17-jre-alpine`) para o MVP do Hackathon.

* **Por que Alpine?** É extremamente leve (5MB de base) mas ainda possui um shell (`sh`). Isso é crítico para debug rápido (testes de conectividade, verificação de arquivos) durante a pressão do evento.
* **Por que não Distroless agora?** Imagens "Distroless" (Google) são mais seguras por não terem shell, mas tornam o debug muito difícil. Decidimos não correr esse risco operacional durante os 4 dias de desenvolvimento.

## Roadmap de Melhoria (Se sobrar tempo)
Caso o projeto esteja estável antes do prazo final, podemos migrar para **Distroless** para endurecer a segurança (Hardening).

**Alteração necessária:**
Mudar a linha:
`FROM eclipse-temurin:17-jre-alpine`
Para:
`FROM gcr.io/distroless/java17-debian11`