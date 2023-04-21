/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.loggenerator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class LogGenerator {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    
    private static String getRANDOMLines(Random random, String file) throws IOException{
        List<String> lines = Files.readAllLines(Paths.get(file));
        return lines.get(random.nextInt(lines.size()));
    }
    
    
    private static String generateDate(Random random) {
        Calendar calendar = Calendar.getInstance();

        int year = random.nextInt(2) + 2023;
        int month = random.nextInt(12);
        int day = random.nextInt(calendar.getActualMaximum(Calendar.DAY_OF_MONTH)) + 1;

        calendar.set(year, month, day);

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
                cpf += String.format("%d", random.nextInt(10));
            }  
            if(i != 2)
                cpf += String.format(".");
        }
        cpf += String.format("-%d%d", random.nextInt(10), random.nextInt(10));
        return cpf;
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

    private static void grammarPrepare(Random random) throws InterruptedException, IOException {
        for (int i = 0; i < 100; i++) {
            String cpf = generateCPF(random);
            String action = getRANDOMLines(random, "LogGenerator/text_files/action.txt");
            String message = getRANDOMLines(random, "LogGenerator/text_files/messages.txt");
            String to_user = generateCPF(random);
            String id = generateRandomId();
            String fileName = getRANDOMLines(random, "LogGenerator/text_files/files.txt");
            String content = getRANDOMLines(random, "LogGenerator/text_files/contents.txt");
            String name = getRANDOMLines(random, "LogGenerator/text_files/names.txt");
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
                .replace("{TO_USER}", "<s>" + to_user + "</s>")
                .replace("{ACTION}", action)
                .replace("{DATETIME}", DATE_FORMAT.format(new Date()));

            System.out.println(message);
        }
    }
    
    private static void printMessages(Random random, int n) throws IOException, InterruptedException{
        for (int i = 0; i < n; i++) {
            String cpf = generateCPF(random);
            String action = getRANDOMLines(random, "LogGenerator/text_files/action.txt");
            String message = getRANDOMLines(random, "LogGenerator/text_files/messages.txt");
            String to_user = generateCPF(random);
            String id = generateRandomId();
            String fileName = getRANDOMLines(random, "LogGenerator/text_files/files.txt");
            String content = getRANDOMLines(random, "LogGenerator/text_files/contents.txt");
            String name = getRANDOMLines(random, "LogGenerator/text_files/names.txt");
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
                .replace("{CPF}",  cpf )
                .replace("{CONTENT}", content)
                .replace("{FILE_NAME}", fileName)
                .replace("{ID}", id)
                .replace("{TO_USER}", to_user )
                .replace("{ACTION}", action)
                .replace("{DATETIME}", DATE_FORMAT.format(new Date()));

            System.out.println(message);
            TimeUnit.SECONDS.sleep(1);
        }
    }
    
    public static void main(String[] args) throws InterruptedException, IOException {
        System.setOut(new CustomPrintStream(System.out));

        Random random = new Random();
        grammarPrepare(random);
        int numMessages = 500; 
        printMessages(random, numMessages);
    }
}
