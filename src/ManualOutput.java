
public class ManualOutput {
    public static void main(final String[] args) {
        String[] param = new String[2];

        param[0] = "/home/tibi/Automatica/an2/sem1/" +
                "POO/teme/proiect2/" +
                "private_proiect_etapa2_POO/checker/resources/in/complex_4.json";
        param[1] = "/home/tibi/Automatica/an2/sem1/" +
                "POO/teme/proiect2/private_proiect_etapa2_POO/checker/my_out/complex_4_out.json";

        try {
            Main.main(param);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
