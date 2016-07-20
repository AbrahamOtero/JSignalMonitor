package net.javahispano.jsignalmonitor;

import java.util.*;

import net.javahispano.jsignalmonitor.annotations.*;


/**
 * <p>Title: Herraienta de monitorizacion</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 1999</p>
 * @version 	0.6
 * @author 	Abraham Otero Quintana
 */

public abstract class ClientAdapter implements Client {

  public ClientAdapter() {
  }

  /**
   * Sobreescribe el correspondiente método de la interfaz Client.
   * @param num_canal int
   * @param primero int
   * @param ultimo int
   * @return Object
   */
  public abstract Object getData(int num_canal, int primero, int ultimo);

  /**
   * Sobreescribe el correspondiente método de la interfaz Client.
   * @param num_canal int
   * @param primero int
   * @param ultimo int
   * @return byte[]
   */
  public byte[] getPossibility(int num_canal, int primero, int ultimo) {
    return null;
  }

  /**
   * Sobreescribe el correspondiente método de la interfaz Client.
   * @param num_canal int
   * @param primero int
   * @param ultimo int
   * @return LinkedList
   */
  public LinkedList getMarks(int num_canal, int primero, int ultimo) {
    return null;
  }

  /**
   * Sobreescribe el correspondiente método de la interfaz Client.
   * @param primero int
   * @param ultimo int
   * @return LinkedList
   */
  public LinkedList getAnnotations(int primero, int ultimo) {
    return null;
  }

  /**
   * Sobreescribe el correspondiente método de la interfaz Client.
   * @param anotacion Annotation
   */
  public void deleteAnnotation(Annotation anotacion) {

  }

  /**
   * Sobreescribe el correspondiente método de la interfaz Client.
   * @param canal int
   * @param anotacion Mark
   */
  public void deleteMark(int canal, Mark anotacion) {

  }

  /**
   * Sobreescribe el correspondiente método de la interfaz Client.
   * @param canal int
   * @param marca Mark
   */
  public void anadMark(int canal, Mark marca) {

  }

  /**
   *
   * @return Locale
   */
  public Locale getLocale() {
    return null;
  }

  /**
   *Sobreescribe el correspondiente método de la interfaz Client.
   * @param anotacion Annotation
   */
  public void addAnnotation(Annotation anotacion) {
  }

  /**
   * Sobreescribe el correspondiente método de la interfaz Client.
   */

  public void selectionFinished(int num_canal, int t1, int t2) {
  }

  /**
   * Sobreescribe el correspondiente método de la interfaz Client.
   * @return int
   */
  public int getTotaNumberOfData() {
    return 0;
  }
}
