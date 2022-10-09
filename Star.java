package application;

public class Star {

    // Single line declaring String, Int, Double
    private String name, id, constellationName, declination;
    private int rightAscensionHours, rightAscensionMinutes, declinationDegrees, declinationMinutes;
    private double rightAscensionSeconds, declinationSeconds, visualMagnitude, distance;

    // Method to stringify/tokenize string for further use
    public Star(String string) {
        String token;
        String[] tokens = string.trim().split(",");

        // Check tokens in array
        // STRINGS
        this.name = tokens[1].trim().length() > 0 ? tokens[1].trim() : "unnamed";
        this.id = tokens[2].trim().length() > 0 ? tokens[2].trim() : "unnamed";
        this.constellationName = tokens[7].trim().length() > 0 ? tokens[7].trim() : "unnamed";
        this.declination = tokens[11].trim().length() > 0 ? tokens[11].trim() : "undefined";

        // INTEGERS
        token = tokens[9].trim();
        if (token.length() > 0)
            this.rightAscensionMinutes = Integer.parseInt(token);
        else
            this.rightAscensionMinutes = -1;
        token = tokens[12].trim();
        if (token.length() > 0)
            this.declinationDegrees = Integer.parseInt(token);
        else
            this.declinationDegrees = -1;
        token = tokens[13].trim();
        if (token.length() > 0)
            this.declinationMinutes = Integer.parseInt(token);
        else
            this.declinationMinutes = -1;

        // DOUBLES
        token = tokens[10].trim();
        if (token.length() > 0)
            this.rightAscensionSeconds = Double.parseDouble(token);
        else
            this.rightAscensionSeconds = -1.0;
        token = tokens[14].trim();
        if (token.length() > 0)
            this.declinationSeconds = Double.parseDouble(token);
        else
            this.declinationSeconds = -1.0;
        token = tokens[15].trim();
        if (token.length() > 0)
            this.visualMagnitude = Double.parseDouble(token);
        else
            this.visualMagnitude = -1.0;
        token = tokens[21].trim();
        if (token.length() > 0)
            this.distance = Double.parseDouble(token);
        else
            this.distance = -1.0;
    }

    // GETTERS (DONT TOUCH, DONT LOOK)
    public String getConstellationName() {
        return this.constellationName;
    }

    public double getVisualMagnitude() {
        return this.visualMagnitude;
    }

    public double getDistance() {
        return this.distance;
    }
    
    public String getName() {
    	return this.name;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        // Rubric for displaying found stars <Brightest/Farthest> to the user in panel
        sb.append(String.format("Name:                      %s\n", this.name));
        sb.append(String.format("ID:                            %s\n", this.id));
        sb.append(String.format("Right Ascension:      %dh %dm %.2fs\n", rightAscensionHours, rightAscensionMinutes,
                rightAscensionSeconds));
        sb.append(String.format("Declination:              %s %d\u00b0 %d' %.1f\" \n", this.declination,
                this.declinationDegrees, this.declinationMinutes, this.declinationSeconds));
        sb.append(String.format("Visual Magnitude:    %.2f\n", this.visualMagnitude));
        sb.append(String.format("Distance:                  %.2f light years\n", this.distance));
        return sb.toString();
    }
}
