package repo;

import component.mesh.Mesh;
import types.Color;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MeshManager {
        Map<Long, Mesh> meshSet;

        public MeshManager()
        {
                this.meshSet = new HashMap<Long, Mesh>();
        }

        public Mesh createMesh(String name, float[] vx, float[] vy, short[] vertices, int[] colors)
        {
                Mesh mesh = new Mesh(name, vx, vy, vertices, colors);

                return meshSet.put(mesh.getId(), mesh);
        }

        public Map<Long, Mesh> getMeshSet()
        {
                return meshSet;
        }

        public Mesh createFromFile(String fileName)
        {
                Mesh mesh = loadFromFile(fileName);

                if (mesh == null)
                        return null;

                meshSet.put(mesh.getId(), mesh);

                return mesh;
        }

        public static void storeToFile(Mesh mesh, String fileName)
        {
                try {
                        FileWriter writer = new FileWriter(fileName);

                        writer.write(mesh.toString());
                        writer.close();
                } catch (IOException e) {
                        e.printStackTrace();
                }
        }

        public static Mesh loadFromFile(String fileName)
        {
                Scanner rd = null;

                try {
                        File file = new File(fileName);
                        rd = new Scanner(file);
                } catch (FileNotFoundException e) {
                        rd.close();
                        e.printStackTrace();
                }

                boolean foundMesh = false;
                String[] words;
                float[] x = null;
                float[] y = null;
                short[] vertices = null;
                int[] colors = null;
                String tag = "mesh";
                Color color = new Color();
                short lineNum = 0;

                System.out.println("# loading \"" + fileName + "\"");

                while (rd.hasNextLine()) {
                        ++lineNum;
                        words = getWords(rd.nextLine());

                        if (words == null)
                                continue;

//            System.out.println("# " + lineNum + ") " + Arrays.toString(words));

                        if (foundMesh) {
                                if (0 == words[0].compareTo("mesh") &&
                                        0 == words[1].compareTo("end")) {

                                        System.out.println("# mesh end");
                                        break;
                                }

                                short i;

                                switch (words[0].charAt(0)) {
                                case 'v':
                                        i = Short.parseShort(words[1]);

                                        x[i] = Float.parseFloat(words[2]);
                                        y[i] = Float.parseFloat(words[3]);
                                        break;

                                case 't':
                                        i = Short.parseShort(words[1]);
                                        i *= 3;
                                        vertices[i] = Short.parseShort(words[2]);
                                        vertices[i + 1] = Short.parseShort(words[3]);
                                        vertices[i + 2] = Short.parseShort(words[4]);
                                        break;

                                case 'c':
                                        i = Short.parseShort(words[1]);
                                        color.set(Integer.parseInt(words[2]),
                                                Integer.parseInt(words[3]),
                                                Integer.parseInt(words[4]),
                                                Integer.parseInt(words[5])
                                        );

                                        colors[i] = color.get();
                                        break;

                                default:
                                        System.out.println("# File: \"" + fileName
                                                + "\", line: " + lineNum
                                                + ", invalid format: \"" + words[0] + "\" -- ignored");
                                }
                        } else if (0 == words[0].compareTo("mesh")) {
                                foundMesh = true;

                                short nv = Short.parseShort(words[1]);
                                short nt = Short.parseShort(words[2]);
                                short nc = Short.parseShort(words[3]);

                                tag = words[4];
                                x = new float[nv];
                                y = new float[nv];
                                vertices = new short[nt];

                                if (nc > 0 && nc == (nt / 3))
                                        colors = new int[nc];

                                System.out.println("# found mesh \"" + tag + "\", vertices: " + nv
                                        + ", triangles: " + (nt / 3)
                                        + ", colors: " + nc
                                );
                        }
                }

                rd.close();

                if (foundMesh) {
                        System.out.println("# \"" + fileName + "\", " + lineNum
                                + " lines, mesh \"" + tag + "\" done");

                        return new Mesh(tag, x, y, vertices, colors);
                }

                return null;
        }

        private static String[] getWords(String line)
        {
                String[] words;
                line.trim();

                if (line.isEmpty())
                        return null;

                if (line.charAt(0) == '#')
                        return null;

                words = line.split("[ \t]+");
                return words;
        }
}
