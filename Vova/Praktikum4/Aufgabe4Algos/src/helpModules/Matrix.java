package helpModules;

public interface Matrix {

    /**
     * Gibt einen Element der MAtrix zur�ck
     *
     * @param i, der Zeilenindex wo der Element sich befindet
     * @param j, der Spaltenindex wo der Elemnt sich befindet
     * @return
     */
    Integer getAt(int i, int j);

    /**
     * Setzt einen ELemnt der Matrix.
     *
     * @param i, der Zeilenindex wo der Element sich befindet
     * @param j, der Spaltenindex wo der Elemnt sich befindet
     * @param elem
     */
    void setAt(int i, int j, Integer elem);

    /**
     * Mathematisch korrekte L�sung w�re besser.
     *
     * @return
     */
    int getDem();
    
    /**
     * Setzt die Werte in der Matrix für IJ und JI.
     * @param i
     * @param j 
     * @param elem 
     */
    void setBoth(int i, int j, Integer elem);

    /**
     * Gibt den Zeilenindex von dem gesuchten Element in einer angegebener Spalte
     * @param column
     * @param elem
     * @return 
     */
    int findInColumn(int column, int elem);
}
