/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package signer;

import java.io.File;
import java.io.Serializable;
import java.time.LocalDateTime;

public class SignedObject implements Serializable {

    File document;
    byte[] docSign, sourceSign, key;
    LocalDateTime date;

    public SignedObject() {
        date = LocalDateTime.now();
    }

    public LocalDateTime getDate() {
        return date;
    }

    public byte[] getDocSign() {
        return docSign;
    }

    public void setDocSign(byte[] docSign) {
        this.docSign = docSign;
    }

    public byte[] getSourceSign() {
        return sourceSign;
    }

    public void setSourceSign(byte[] sourceSign) {
        this.sourceSign = sourceSign;
    }
    String source;

    public File getDocument() {
        return document;
    }

    public void setDocument(File document) {
        this.document = document;
    }

    public byte[] getKey() {
        return key;
    }

    public void setKey(byte[] key) {
        this.key = key;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

}
