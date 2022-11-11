import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Set;
import java.io.FileNotFoundException;   //Excepcion al no detectar el archicvo al cual piensa acceder
import java.io.BufferedReader;          //Permite la implementacón de la clase buffered reader

public class AddressBook
{
    private LinkedHashMap<String, String> listaContactos = new LinkedHashMap<>();   //Esta lista actuará como un intermediario entre la interfaz y el archivo de texto, antes de realizar un cambio en el archivo, se realizan los cambios aquí

    public void load() throws IOException, FileNotFoundException    //Carga los valores del archivo de texto al LinkedHashMap
    {
        FileReader textoCompleto = new FileReader("AddressBook.txt");   //El file reader que permitirá leer el archivo
        BufferedReader bufred = new BufferedReader(textoCompleto);              //Se genera el buffered reader
        String linea;                                                           //ALmacena cada linea del documento
        while((linea = bufred.readLine())!= null)                               //Mientras no se haya llegado al final del archivo
        {
            String [] lineaSeparada = linea.split(",");                   //Separa la linea obtenida por las comas, teniendo por separaado el teléfono y el nombre al que le corresponde

            listaContactos.put(lineaSeparada[0], lineaSeparada[1]);             //Se coloca en la LinkedHashMap el telefono como llave y el nombre como valor
        }

        bufred.close();     //Se cierra el BufferedReader
    }

    public void write() throws IOException      //Escribe el contenido del LinkedHashMap en el archivo
    {
        FileWriter meterLista = new FileWriter("AddressBook.txt");     //Se genera un filewriter paar el archivo

        Set llavesSet = listaContactos.keySet();    //Se obtienen las llaves del LinkedHashMap

        ArrayList llavesList = new ArrayList<String>();     //Las llaves obtenidas se guardan en un ArrayList
        llavesList.addAll(llavesSet);

        for(int i = 0; i < listaContactos.size(); i++)    //Se inserta cada contacto en el archivo de texto
        {
            if(i == 0)  //Si es la primera insercion
            {
                meterLista.write(llavesList.get(i)+","+listaContactos.get(llavesList.get(i))+"\n");
            }

            else    //Todas las demas
            {
                meterLista.append(llavesList.get(i)+","+listaContactos.get(llavesList.get(i))+"\n");
            }
        }
        meterLista.close();  //Se cierra el File Writer
    }

    public void desplegarLista()    //Despliega la lista completa de contactos en pantalla
    {
        if(!listaContactos.isEmpty())   //Se comprueba que la lista tenga contenido dentro de si, en caso de tenerlo: la LinkedHashMap se transforma en una String que se pueda desplegar en pantalla
        {
            String lista = hashMapToString();

            JOptionPane.showMessageDialog(null,lista, "ACTIVIDAD 12 Lista de Contactos", JOptionPane.INFORMATION_MESSAGE);
        }

        else    //En caso de estar vacía, se notifica al usuario que no hay nada que desplegar
        {
            JOptionPane.showMessageDialog(null,"No hay nada guardado en la lista de contactos", "ACTIVIDAD 12 Lista de Contactos", JOptionPane.INFORMATION_MESSAGE);
        }

    }

    public void guardarContacto() throws IOException    //Se crea y se almacena un nuevo contacto en la LinkedHashMap
    {

        String telefono, nombre;    //Variables que almacenaran los valores del nuevo contacto
        boolean telefonoValido, nombreValido, ingresarOtro;     //Bolleans que evaluan la situación

        do  //Ciclo que se repetirá hasta que el usuario ya no quiera agregar más contactos
        {
            ingresarOtro = false;

            do  //Ciclo que se repetirá hasta que el usuario ingrese un teléfono considerado válido para el programa
            {
                telefonoValido = true;
                telefono = (String) JOptionPane.showInputDialog(null, "Ingrese el telefono del nuevo contacto", "ACTIVIDAD 12 Lista de Contactos", JOptionPane.QUESTION_MESSAGE);   //Se pide el teléfono

                if(!telefono.matches("^[0-9]+$") || telefono.isBlank() || telefono.length() != 10)      //Se evalua el valor ingresado por el usuario
                {
                    JOptionPane.showMessageDialog(null,"El telefono no posee un formato valido:\n\n\t1) Solamente pueden ser digitos (0-9)\n\t2) No puede ser un valor nulo o vacio\n\t3) El telefono debe ser de 10 digitos", "ACTIVIDAD 12 Lista de Contactos", JOptionPane.INFORMATION_MESSAGE);
                    telefonoValido = false;
                }
            }
            while(!telefonoValido);

            do  //Ciclo que se repetirá hasta que el usuario ingrese un nombre considerado válido para el programa
            {
                nombreValido = true;
                nombre = (String) JOptionPane.showInputDialog(null, "Ingrese el nombre del nuevo contacto", "ACTIVIDAD 12 Lista de Contactos", JOptionPane.QUESTION_MESSAGE);     //Se pide el nombre del contacto

                if(!nombre.matches("^[a-zA-ZñÑ ]+$") || nombre.isBlank())   //Se evalua el valor ingresado por el usuario
                {
                    JOptionPane.showMessageDialog(null,"El nombre no posee un formato valido:\n\n\t1) Solamente letras\n\t2) No puede ser un valor nulo o vacio", "ACTIVIDAD 12 Lista de Contactos", JOptionPane.INFORMATION_MESSAGE);
                    nombreValido= false;
                }
            }
            while(!nombreValido);

            //Se guarda el contacto creado en el LinkedHashMap
            listaContactos.put(telefono, nombre);

            //Se pregunta si desea añadir otro contacto
            int  repetir = JOptionPane.showConfirmDialog(null,"Quiere agregar otro numero", "ACTIVIDAD 12 Lista de Contactos", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

            //Si presiona Si, se repite el proceso
            if(repetir == JOptionPane.YES_OPTION)
            {
                ingresarOtro = true;
            }
        }
        while(ingresarOtro);

        //Todos los contactos creados son guardados en el archivo de texto
        this.write();
    }

    public void borrarContacto() throws IOException     //Se borra un contacto del LinkedHashMap
    {
        if(listaContactos.isEmpty())    //Si la lista está vacia, se informa que no hay nada que borrar y se cancela el proceso
        {
            JOptionPane.showMessageDialog(null, "No hay nada que borrar, la lista está vacía","ACTIVIDAD 12 Lista de Contactos", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean borrarOtro;     //Evalua si querer borrar otro contacto
        String telefonoBorrar;  //Donde se almacena el telefono que se desea borrar de la lista

        //Se recuperan las llaves del LinkedHashMap y se guardan en un ArrayList
        Set llavesSet = listaContactos.keySet();
        ArrayList llavesList = new ArrayList<String>();
        llavesList.addAll(llavesSet);

        do  //Este ciclo se repetirá hasta que el usuario ya no quiera retirar mas contactos de la lista
        {
            borrarOtro = false;

            //Se toma el ArrayList actual y se transforma en un Array
            Object[] llavesArray = llavesList.toArray();

            //Se le despliega al usuario la lista de telefonos que se pueden borrar
            telefonoBorrar = (String) JOptionPane.showInputDialog(null,"Seleccione el telefono que desea borrar:", "ACTIVIDAD 12 Lista de Contactos", JOptionPane.QUESTION_MESSAGE, null, llavesArray, llavesArray[0]);

            //Se remueve el teléfono del ArrayList, esto es en caso de que, si quiere borrar otro contacto, el que se eliminó anteriormente ya no aparezca en la lista de opciones
            llavesList.remove(telefonoBorrar);

            //Se elimina el contacto deL LinkedHashMap
            listaContactos.remove(telefonoBorrar);

            //Si la lista aun posee contactos, se pregunta si desea eliminar algún otro
            if(listaContactos.size() > 0)
            {
                int repetir = JOptionPane.showConfirmDialog(null,"Quiere borrar otro telefono?", "ACTIVIDAD 12 Lista de Contactos", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                if(repetir == JOptionPane.YES_OPTION)
                {
                    borrarOtro = true;
                }
            }

            //Si la lista ya no tiene contactos que eliminar, se avisa al usuario y se regresa al menú principal
            else
            {
                JOptionPane.showMessageDialog(null, "Se ha eliminado el último contacto de la lista, ya no hay nada mas que borrar", "ACTIVIDAD 12 Lista de Contactos", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        while(borrarOtro);

        //Se guardan los cambios al LinkedHashMap en el archivo
        this.write();
    }

    public String hashMapToString()     //Se transforma la lista de contactos a una sola String de tal forma que pueda desplegarse en la interfaz
    {
        String listaAString = "Lista de contactos actual:\n\n";

        Set llavesSet = listaContactos.keySet();

        ArrayList llavesList = new ArrayList<String>();

        llavesList.addAll(llavesSet);

        for(int i = 0; i< llavesList.size(); i++)
        {
            if(i == llavesList.size())
            {
                listaAString += llavesList.get(i) + " : " + listaContactos.get(llavesList.get(i));
            }

            else
            {
                listaAString += llavesList.get(i) + " : " + listaContactos.get(llavesList.get(i)) + "\n";
            }

        }

        return  listaAString;
    }

}