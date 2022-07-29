package dev.ojvzinn.aplicativo;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Main {

    public static boolean isConfig = false;
    public static Integer sizeText;
    public static Integer linesText;
    public static Integer variacoes;
    public static String algoritimo;
    public static TypeText type;
    public static List<String> variaveis = new ArrayList<>();
    public static List<String> finalVariaveis = new ArrayList<>();

    public static void main(String[] args) {
        createWindow();
    }


    public static void createWindow() {
        JFrame jFrame = new JFrame("Gerador de Texto");
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton button = new JButton("CLIQUE PARA SELECIONAR O ARQUIVO PARA CONFIGURAR O SEU TEXTO!");
        jFrame.setContentPane(button);

        button.addActionListener(e -> {
            selectFile(jFrame, button);
        });

        button.setBorderPainted(true);
        jFrame.getContentPane().setBackground(Color.white);
        jFrame.setLocale(null);
        jFrame.pack();
        jFrame.setVisible(true);

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        jFrame.setSize(dimension.width, dimension.height);
        jFrame.setLocationRelativeTo(null);
    }

    public static void selectFile(JFrame jFrame, JButton jButton) {
        JFileChooser a = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        a.setDialogTitle(isConfig ? "Selecione aqui o arquivo que você quer salvar o seu texto!" : "Selecione aqui o arquivo que você quer puxar as configs do seu texto!");
        a.setFileSelectionMode(JFileChooser.FILES_ONLY);

        int returnValue = a.showSaveDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            if (isConfig) {
                String location = a.getSelectedFile().getPath();
                if (type.equals(TypeText.FLASHNICKS) || type.equals(TypeText.BALBNICKS) || type.equals(TypeText.NICKS)) {
                    setNicksToText(location);
                } else {
                    setRandomToText(sizeText, location, linesText);
                }
                jFrame.dispose();
            } else {
                isConfig = true;
                jFrame.remove(jButton);
                JButton button = new JButton("CLIQUE PARA SELECIONAR O ARQUIVO PARA SALVAR O SEU TEXTO!");
                jFrame.setContentPane(button);

                button.addActionListener(e -> selectFile(jFrame, button));
                jFrame.setContentPane(button);
                jFrame.pack();

                Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
                jFrame.setSize(dimension.width, dimension.height);
                jFrame.setLocationRelativeTo(null);
                File file = new File(a.getSelectedFile().getPath());
                try {
                    Scanner scanner = new Scanner(file);
                    List<String> b = new ArrayList<>();
                    while (scanner.hasNext()) {
                        String ll = scanner.nextLine();
                        b.add(ll);
                    }
                    b.remove(2);
                    StringJoiner stringJoiner = new StringJoiner(",");
                    for (String n : b) {
                        stringJoiner.add(n);
                    }
                    System.out.print(stringJoiner);
                    linesText = Integer.parseInt(stringJoiner.toString().split(",")[0].split(": ")[1]);
                    sizeText = Integer.parseInt(stringJoiner.toString().split(",")[1].split(": ")[1]);
                    algoritimo = stringJoiner.toString().split(",")[2].split(": ")[1];
                    variacoes = Integer.parseInt(stringJoiner.toString().split(",")[3].split(": ")[1]);
                    type = TypeText.getFromName(stringJoiner.toString().split(",")[4].split(": ")[1]);
                    if (type != null) {
                        if (type.equals(TypeText.FLASHNICKS) || type.equals(TypeText.BALBNICKS) || type.equals(TypeText.NICKS)) {
                            variaveis.add(stringJoiner.toString().split(",")[5].split(": ")[1]);
                            for (int nn = 6; nn < Arrays.stream(stringJoiner.toString().split(",")).count(); nn++) {
                                variaveis.add(stringJoiner.toString().split(",")[nn]);
                            }
                            getVariations();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void setRandomToText(Integer sizeText, String patch, Integer lines) {
        List<String> p = new ArrayList<>();

        for (int d = 0; d < lines; d++) {
            p.add(getRandom(sizeText));
        }

        StringJoiner stringJoiner = new StringJoiner("\r\n");

        for (String o : p) {
            stringJoiner.add(o);
        }

        try {
            FileWriter fileWriter = new FileWriter(patch);
            fileWriter.write(stringJoiner.toString());
            fileWriter.close();
        } catch (Exception e) {
            System.exit(0);
        }
    }

    public static void setNicksToText(String patch) {
        StringJoiner stringJoiner = new StringJoiner("\r\n");

        for (String o : finalVariaveis) {
            stringJoiner.add(o);
        }

        try {
            FileWriter fileWriter = new FileWriter(patch);
            fileWriter.write(stringJoiner.toString());
            fileWriter.close();
        } catch (Exception e) {
            System.exit(0);
        }
    }

    public static String getRandom(Integer sizeText) {
        List<String> b = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < sizeText; i++) {
            char c = algoritimo.equalsIgnoreCase("ALFABETO")  ? (char) (random.nextInt(variacoes) + 'a') : algoritimo.equalsIgnoreCase("Ψ") ? (char) (random.nextInt(variacoes) + 'Ψ') : (char) (random.nextInt(variacoes) + '0');
            b.add(String.valueOf(c));
        }
        StringJoiner stringJoiner = new StringJoiner("");
        for (String s : b) {
            stringJoiner.add(s);
        }
        return stringJoiner.toString();
    }

    public static void getVariations() {
        if (type.equals(TypeText.FLASHNICKS)) {
            for (int i = 0; i < variaveis.size() * 5000; i++) {
                finalVariaveis.add("Flash" + variaveis.get(ThreadLocalRandom.current().nextInt(variaveis.size())) + ThreadLocalRandom.current().nextInt(5000));
            }
        } else if (type.equals(TypeText.NICKS)) {
            for (int i = 0; i < variaveis.size() * 5000; i++) {
                finalVariaveis.add(variaveis.get(ThreadLocalRandom.current().nextInt(variaveis.size())) + ThreadLocalRandom.current().nextInt(5000));
            }
        } else {
            for (int i = 0; i < variaveis.size() * 5000; i++) {
                finalVariaveis.add("Balb" + variaveis.get(ThreadLocalRandom.current().nextInt(variaveis.size())) + ThreadLocalRandom.current().nextInt(5000));
            }
        }
    }
}
