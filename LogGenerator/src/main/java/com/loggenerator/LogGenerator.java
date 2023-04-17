/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.loggenerator;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class LogGenerator {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final String[] ACTIONS = {"login", "logout", "create_user", "delete_user", "update_profile", "search", "add_to_cart", "checkout", "send_message", "share", "download", "upload", "play", "stop", "like"};
    private static final String[] FILE_NAMES = {"document.pdf", "presentation.pptx", "image.jpg", "video.mp4", "spreadsheet.xlsx", "code.java", "text.txt", "audio.mp3", "archive.zip", "database.db"};
    private static final String[] CONTENTS = {"um novo post de blog", "um meme engracado", "um artigo provocador de pensamentos", "uma bela fotografia", "um tutorial util", "uma historia emocionante", "um podcast interessante", "um infografico util", "uma citacao inspiradora"};
    private static final String[] NAMES = {
        "Alice", "Bob", "Charlie", "David", "Emma", "Frank", "Grace",
        "Henry", "Isabella", "Jacob", "Kate", "Liam", "Mia", "Nathan",
        "Olivia", "Peter", "Quinn", "Rose", "Sarah", "Tom", "Uma",
        "Victor", "Wendy", "Xander", "Yara", "Zack", "Adam", "Bella",
        "Cameron", "Diana", "Ethan", "Fiona", "Gavin",
        "Hazel", "Ian", "Julia", "Kai", "Lila", "Mason", "Nora",
        "Owen", "Piper", "Quincy", "Riley", "Sofia", "Tyler", "Violet",
        "Wyatt", "Ximena", "Yvette", "Zara"
    };
    private static final String[] MESSAGESWCPF = {"O usuario {CPF} realizou {ACTION} em {DATETIME}.",
        "Um novo usuario {CPF} foi criado em {DATETIME}.",
        "O perfil do usuario {CPF} foi atualizado em {DATETIME}.",
        "O produto com ID {ID} foi adicionado ao carrinho pelo usuario {CPF} em {DATETIME}.",
        "O usuario {CPF} finalizou sua compra em {DATETIME}."};

    private static final String[] MESSAGES = {"O usuario {CPF} realizou {ACTION} em {DATETIME}.",
        "Um novo usuario {CPF} foi criado em {DATETIME}.",
        "O usuario {CPF} fez login em {DATETIME}.",
        "O usuario {CPF} fez logout em {DATETIME}.",
        "O perfil do usuario {CPF} foi atualizado em {DATETIME}.",
        "O usuario {CPF} deletou sua conta em {DATETIME}.",
        "O produto com ID {ID} foi adicionado ao carrinho pelo usuario {CPF} em {DATETIME}.",
        "O usuario {CPF} finalizou sua compra em {DATETIME}.",
        "O usuario {CPF} enviou uma mensagem para o usuario {TO_USER} em {DATETIME}.",
        "O usuario {CPF} compartilhou {CONTENT} com seus seguidores em {DATETIME}.",
        "O usuario {CPF} fez o download de {FILE_NAME} em {DATETIME}.",
        "Um novo produto foi adicionado ao inventario em {DATETIME}.",
        "Um pedido foi feito pelo cliente {CUSTOMER_ID} em {DATETIME}.",
        "Um pagamento de {AMOUNT} foi processado em {DATETIME}.",
        "Um novo post de blog foi publicado em {DATETIME}.",
        "Um novo evento foi agendado para {DATE} as {TIME}.",
        "Uma nova candidatura de emprego foi recebida em {DATETIME}.",
        "Uma nova mensagem foi recebida de {SENDER_NAME} em {DATETIME}.",
        "Uma nova assinatura foi criada por {CUSTOMER_NAME} em {DATETIME}.",
        "Um novo arquivo foi enviado para o servidor em {DATETIME}.",
        "Uma nova venda foi criada em {DATETIME}.",
        "Um novo plano de assinatura foi criado em {DATETIME}.",
        "Uma nova vaga de emprego foi publicada em {DATETIME}.",
        "Um novo relatorio de erro foi enviado em {DATETIME}.",
        "Uma nova avaliacao de produto foi enviada por {USER_NAME} em {DATETIME}.",
        "Uma nova inscricao em evento foi recebida de {USER_NAME} em {DATETIME}.",
        "Uma nova consulta foi agendada para {DATE} as {TIME}."};

    private static String generateDate(Random random) {
        Calendar calendar = Calendar.getInstance();

        // Gerando ano aleatorio entre 1950 e 2023
        int year = random.nextInt(2) + 2023;
        // Gerando mês aleatorio (valores vao de 0 a 11)
        int month = random.nextInt(12);
        // Gerando dia aleatorio (valores vao de 1 a 28, 29, 30 ou 31)
        int day = random.nextInt(calendar.getActualMaximum(Calendar.DAY_OF_MONTH)) + 1;

        // Configurando data gerada no calendario
        calendar.set(year, month, day);

        // Obtendo data no formato desejado
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String data = dateFormat.format(calendar.getTime());
        return data;
    }

    public static String generateHours(Random random) {
        int hora = random.nextInt(24);
        int minuto = random.nextInt(60);

        String horaFormatada = String.format("%02d:%02d", hora, minuto);
        return horaFormatada;
    }

    private static String generateCPF(Random random) {
        String cpf = "";        
        for (int i = 0; i < 3; i++) {
            for (int j = 0; i < 3; i++) {
                cpf += String.format("%d", random.nextInt(10))
            }    
            cpf += String.format(".");
        }
        cpf.remove(cpf.length());
        cpf += String.format("-%d%d", random.nextInt(10), random.nextInt(10));
        return cpf
    }

    public static String generateRandomId() {
        String validChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 7; i++) {
            sb.append(validChars.charAt(random.nextInt(validChars.length())));
        }
        return sb.toString();
    }

    private static void grammarPrepare(Random random) throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            String cpf = generateCPF(random);
            String action = ACTIONS[random.nextInt(ACTIONS.length)];
            String message = MESSAGESWCPF[i%5];
            String to_user = generateCPF(random);
            String id = generateRandomId();
            String fileName = FILE_NAMES[new Random().nextInt(FILE_NAMES.length)];
            String content = CONTENTS[new Random().nextInt(CONTENTS.length)];
            String name = NAMES[new Random().nextInt(NAMES.length)];
            String amount = "$" + String.valueOf((random.nextInt(99901) + 10) * 100) + ",00";
            String date = generateDate(random);
            String hours = generateHours(random);

            message = message
                .replace("{CUSTOMER_ID}", id)
                .replace("{TIME}", hours)
                .replace("{DATE}", date)
                .replace("{AMOUNT}", amount)
                .replace("{USER_NAME}", name)
                .replace("{SENDER_NAME}", name)
                .replace("{CUSTOMER_NAME}", name)
                .replace("{CPF}", "<s>" + cpf + "</s>")
                .replace("{CONTENT}", content)
                .replace("{FILE_NAME}", fileName)
                .replace("{ID}", id)
                .replace("{TO_USER}", to_user)
                .replace("{ACTION}", action)
                .replace("{DATETIME}", DATE_FORMAT.format(new Date()));

            System.out.println(message);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        System.setOut(new CustomPrintStream(System.out));

        Random random = new Random();
        grammarPrepare(random);
        System.out.println("Start");
        for (int i = 0; i < 100; i++) {

            String cpf = generateCPF(random);
            String action = ACTIONS[random.nextInt(ACTIONS.length)];
            String message = MESSAGES[random.nextInt(MESSAGES.length)];
            String to_user = generateCPF(random);
            String id = generateRandomId();
            String fileName = FILE_NAMES[new Random().nextInt(FILE_NAMES.length)];
            String content = CONTENTS[new Random().nextInt(CONTENTS.length)];
            String name = NAMES[new Random().nextInt(NAMES.length)];
            String amount = "$" + String.valueOf((random.nextInt(99901) + 10) * 100) + ",00";
            String date = generateDate(random);
            String hours = generateHours(random);

            message = message
                .replace("{CUSTOMER_ID}", id)
                .replace("{TIME}", hours)
                .replace("{DATE}", date)
                .replace("{AMOUNT}", amount)
                .replace("{USER_NAME}", name)
                .replace("{SENDER_NAME}", name)
                .replace("{CUSTOMER_NAME}", name)
                .replace("{CPF}", cpf)
                .replace("{CONTENT}", content)
                .replace("{FILE_NAME}", fileName)
                .replace("{ID}", id)
                .replace("{TO_USER}", to_user)
                .replace("{ACTION}", action)
                .replace("{DATETIME}", DATE_FORMAT.format(new Date()));

            System.out.println(message);
            TimeUnit.SECONDS.sleep(1);
        }
    }
}
