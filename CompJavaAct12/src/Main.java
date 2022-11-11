import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, FileNotFoundException    //Método Main
    {
        showMenu();
    }

    public static void showMenu() throws IOException, FileNotFoundException     //Despliega el menu principal de la actividad
    {
       File lista = new File("AddressBook.txt");    //Se crea el archivo

       AddressBook listaContactos = new AddressBook();      //Se crea El AddressBook que permitirá realizar las operaciones

       if(lista.exists())   //Si el archivo ya existe dentro del programa, se cargan los valores, de lo contrario no sehace
       {
           listaContactos.load();
       }

        //Menu de opciones a realizar
        String[] opciones = {
                "A) Mostrar lista de contactos completa",
                "B) Crear nuevo contacto",
                "C) Borrar Contacto",
                "D) Salir del programa"};

        String eleccionCompleta;        //Captura el valor de la string elegida completa
        int salirProceso;               //La opcion que guarda el int que dicta si salir del programa o no
        boolean accionValida = false;   //Boolean que permite que las opciones puedan repetirse indefinidamente hasta que el usuario desee salir del programa

        do
        {
            accionValida = true;

            //Almacena las opciones elegidas por el usuario, tanto para el atributo que desea calcular como la forma que desea usar
            char eleccionSwit;

            try
            {
                //Panel que despliega el atributo a calcular
                eleccionCompleta = (String) JOptionPane.showInputDialog(null,"Seleccione la opcion que desea ejecutar", "ACTIVIDAD 12 Lista de Contactos", JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
                eleccionSwit = eleccionCompleta.charAt(0);

                switch(eleccionSwit) //Dependiendo de la figura elegida, se ejecuta la acción especifica
                {
                    case 'A':
                        listaContactos.desplegarLista();
                        accionValida = false;
                        break;

                    case 'B':
                        listaContactos.guardarContacto();
                        accionValida = false;
                        break;

                    case 'C':
                        listaContactos.borrarContacto();
                        accionValida = false;
                        break;

                    case 'D':
                        JOptionPane.showMessageDialog(null,"Programa terminado", "ACTIVIDAD 12 Lista de Contactos", JOptionPane.INFORMATION_MESSAGE);
                        System.exit(0);
                        break;
                }
            }

            catch(NullPointerException a) //El usuario seleccionó la opcion de cerrar el mensaje o de cancelar
            {
                //Se pregunta si el usuario desea salir del programa usando unicamente la opcion de si o no
                salirProceso = JOptionPane.showConfirmDialog(null,"Quiere salir del programa?", "ACTIVIDAD 12 Lista de Contactos", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                //Si presiona Si
                if(salirProceso == JOptionPane.YES_OPTION)
                {
                    JOptionPane.showMessageDialog(null,"Programa terminado", "ACTIVIDAD 12 Lista de Contactos", JOptionPane.INFORMATION_MESSAGE);
                    System.exit(0);
                }

                //Si presiona No
                else
                {
                    accionValida = false;
                }
            }

            catch(IndexOutOfBoundsException b) //El usuario no ingresó nada y dió aceptar de todas formas
            {
                JOptionPane.showMessageDialog(null,"Comando no reconocido, vuelva a intentarlo", "ACTIVIDAD 12 Lista de Contactos", JOptionPane.ERROR_MESSAGE);
                accionValida = false;
            }
        }
        while(!accionValida);
    }
}

