package GUI;

import java.awt.HeadlessException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import javax.swing.JFileChooser;
import main.FileTools;
import signer.SignManager;
import signer.SignedObject;

public class MenuGUI extends javax.swing.JFrame {

    public MenuGUI() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        CipherButton = new javax.swing.JButton();
        SignButton = new javax.swing.JButton();
        DecipherButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        SignCheckButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Menú");

        CipherButton.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
        CipherButton.setText("Cifrar");
        CipherButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CipherButtonActionPerformed(evt);
            }
        });

        SignButton.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
        SignButton.setText("Firmar");
        SignButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SignButtonActionPerformed(evt);
            }
        });

        DecipherButton.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
        DecipherButton.setText("Descifrar");
        DecipherButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DecipherButtonActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Ubuntu", 1, 36)); // NOI18N
        jLabel1.setText("Menú");
        jLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        SignCheckButton.setFont(new java.awt.Font("Ubuntu", 1, 18)); // NOI18N
        SignCheckButton.setText("Comprobar firma");
        SignCheckButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SignCheckButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(108, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(SignButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(CipherButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(DecipherButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(SignCheckButton, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)))
                .addGap(105, 105, 105))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(30, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(CipherButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(DecipherButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(SignButton, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(SignCheckButton, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //Encriptar
    private void CipherButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CipherButtonActionPerformed
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Seleccionar archivo a encriptar");
        int result = chooser.showOpenDialog(null);
        switch (result) {
            case JFileChooser.APPROVE_OPTION:
                encrypt(chooser.getSelectedFile());
                break;
            case JFileChooser.CANCEL_OPTION:
                System.out.println("Cancel");
                break;
            case JFileChooser.ERROR_OPTION:
                System.out.println("Error");
                break;
        }

    }//GEN-LAST:event_CipherButtonActionPerformed

    private void encrypt(File file) {
        EncryptGUI encryptor = new EncryptGUI();
        encryptor.setFile(file);
        encryptor.setMenu(this);
        encryptor.setVisible(true);
        this.setVisible(false);
    }

    private void decrypt(File file) {
        DecryptGUI decryptor = new DecryptGUI();
        decryptor.setFile(file);
        decryptor.setMenu(this);
        decryptor.setVisible(true);
        this.setVisible(false);
    }

    /*private void askForSignFile(File file) {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Seleccionar archivo de firma");
        int result = chooser.showOpenDialog(null);

        File signFile;

        switch (result) {
            case JFileChooser.APPROVE_OPTION:
                signFile = chooser.getSelectedFile();
                if (FileTools.getExtension(signFile).equals(".sig")) {
                    askForKeyFile(file, signFile);
                } else {
                    DialogManager.showError(this, "Archivo de firma no válido");
                    askForSignFile(file);
                }
                break;
            case JFileChooser.CANCEL_OPTION:
                System.out.println("Cancel");
                break;
            case JFileChooser.ERROR_OPTION:
                System.out.println("Error");
                break;

        }
    }*/

 /*private void askForKeyFile(File file, File signFile) {
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Seleccionar archivo de clave");
        int result = chooser.showOpenDialog(null);
        
        File keyFile;
        
        switch (result) {
            case JFileChooser.APPROVE_OPTION:
                keyFile = chooser.getSelectedFile();
                if (FileTools.getExtension(keyFile).equals(".pk")) {
                    if (SignManager.checkSign(keyFile, signFile, file)) {
                        DialogManager.showDialog(this, "Firma correcta");
                    } else {
                        DialogManager.showError(this, "Firma Incorrecta");
                    }
                    
                } else {
                    DialogManager.showError(this, "Archivo de clave inválido");
                    askForKeyFile(file, signFile);
                }
                break;
            case JFileChooser.CANCEL_OPTION:
                System.out.println("Cancel");
                break;
            case JFileChooser.ERROR_OPTION:
                System.out.println("Error");
                break;
            
        }
    }*/
    //Firmar
    private void SignButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SignButtonActionPerformed
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Seleccionar archivo a firmar");
        int result = chooser.showOpenDialog(null);
        switch (result) {
            case JFileChooser.APPROVE_OPTION:
                File file = chooser.getSelectedFile();
                SignManager.sign(file);
                DialogManager.showDialog(this, "Documento firmado");
                break;
            case JFileChooser.CANCEL_OPTION:
                System.out.println("Cancel");
                break;
            case JFileChooser.ERROR_OPTION:
                System.out.println("Error");
                break;
        }
    }//GEN-LAST:event_SignButtonActionPerformed

    //Desencriptar
    private void DecipherButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_DecipherButtonActionPerformed
        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Seleccionar archivo a desencriptar");
        chooser.setFileFilter(FileTools.getCIPFilter());
        int result = chooser.showOpenDialog(null);
        switch (result) {
            case JFileChooser.APPROVE_OPTION:
                File file = chooser.getSelectedFile();
                if (file.getName().contains(".cip")) {
                    decrypt(chooser.getSelectedFile());
                } else {
                    DialogManager.showError(this, "Archivo no válido");
                }
                break;
            case JFileChooser.CANCEL_OPTION:
                System.out.println("Cancel");
                break;
            case JFileChooser.ERROR_OPTION:
                System.out.println("Error");
                break;

        }
    }//GEN-LAST:event_DecipherButtonActionPerformed

    //Comprobar firma
    private void SignCheckButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SignCheckButtonActionPerformed
        JFileChooser chooser = new JFileChooser();
        chooser.setFileFilter(FileTools.getSGOFilter());
        chooser.setDialogTitle("Seleccionar archivo a comprobar");
        int result = chooser.showOpenDialog(null);

        File file;

        switch (result) {
            case JFileChooser.APPROVE_OPTION:
                file = chooser.getSelectedFile();
                if (FileTools.getExtension(file).equals(".sgo")) {
                    SignedObject sO = SignManager.checkSign(file);
                    if (sO != null) {
                        DialogManager.showDialog(this, "Firma correcta. Emitida por " + formatCI(sO.getSource()) + " el " + formatDate(sO.getDate()));

                        //Recuperar el archivo firmado
                        FileOutputStream fos;
                        try {
                            fos = new FileOutputStream(file.getAbsolutePath().replace(file.getName(), sO.getFileName()));
                            fos.write(sO.getDocument());
                            fos.close();
                            System.out.println("Archivo firmado obtenido");

                        } catch (HeadlessException | IOException e) {

                        }

                    } else {
                        DialogManager.showError(this, "Firma inválida");
                    }
                } else {
                    DialogManager.showError(this, "Archivo inválido");
                }
                //askForSignFile(file);
                break;
            case JFileChooser.CANCEL_OPTION:
                System.out.println("Cancel");
                break;
            case JFileChooser.ERROR_OPTION:
                System.out.println("Error");
                break;

        }
    }//GEN-LAST:event_SignCheckButtonActionPerformed

    public String formatCI(String ci) {
        String s = "";
        int len = ci.length();
        String last_digit = ci.substring(len - 1);
        String last_group = ci.substring(len - 4, len - 1);
        String thousand = ci.substring(1, len - 4);
        String million = ci.substring(0, 1);
        return million + "." + thousand + "." + last_group + "-" + last_digit;
    }

    private String formatDate(LocalDateTime date) {
        Date d = Date.from(date.atZone(ZoneId.systemDefault()).toInstant());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy");
        return sdf.format(d);
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton CipherButton;
    private javax.swing.JButton DecipherButton;
    private javax.swing.JButton SignButton;
    private javax.swing.JButton SignCheckButton;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
