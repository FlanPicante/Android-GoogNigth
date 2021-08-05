package com.example.buenasnoches;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DBFile {
    // Nombre del archivo de la base de datos

    private final String DB_NAME = "bye.db";

    private Context context;

    public DBFile(Context context) {
        this.context = context;
    }

    // Copiar y cargar datos en la base de datos regional
    public String CopyDBFile() throws IOException {

        // Cuando ejecute la aplicación por primera vez, cargue la base de datos en data / data / name del paquete actual / database / <db_name>
        // Obtiene la ruta exacta, context.getPackageName () obtiene el nombre del paquete
        File dir = new File("data/data/" + context.getPackageName() + "/databases");
        // Si la carpeta no existe, crea un archivo
        if (!dir.exists() || !dir.isDirectory()) {
            dir.mkdir();
        }
        // Archivo de declaración
        File file = new File(dir, DB_NAME);
        //Flujo de entrada
        InputStream inputStream = null;
        //Flujo de salida
        OutputStream outputStream = null;
        // Si no existe, escriba el archivo de la base de datos en el directorio de activos en el teléfono a través del flujo de E / S.
        if (!file.exists()) {
            try {
                // Crea un archivo
                file.createNewFile();
                // Cargar archivo por ruta
                inputStream = context.getClass().getClassLoader().getResourceAsStream("assets/" + DB_NAME);
                // Salida a archivo
                outputStream = new FileOutputStream(file);

                byte[] buffer = new byte[1024];
                int len;
                // Escribir por byte
                while ((len = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, len);
                }


            } catch (IOException e) {
                e.printStackTrace();

            } finally {
                // Cerrar recursos
                if (outputStream != null) {

                    outputStream.flush();
                    outputStream.close();

                }
                if (inputStream != null) {
                    inputStream.close();
                }

            }
        }

        return file.getPath();
    }
}
