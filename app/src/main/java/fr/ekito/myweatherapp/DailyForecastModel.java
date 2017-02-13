package fr.ekito.myweatherapp;

public class DailyForecastModel {

    private String forecast;
    private String icon;
    private String temperatureLow;
    private String temperatureHigh;

    /**
     * Business Getter
     */
    public String getTemperatureString() {
        return temperatureLow + "°C - " + temperatureHigh + "°C";
    }

    /**
     * Business Getter
     */
    public String getForecastString() {
        return forecast;
    }


    public DailyForecastModel() {
    }

    public DailyForecastModel(String forecast, String icon, String temperatureLow, String temperatureHigh) {
        this.forecast = forecast;
        this.icon = icon;
        this.temperatureLow = temperatureLow;
        this.temperatureHigh = temperatureHigh;
    }


    public String getForecast() {
        return forecast;
    }

    public void setForecast(String forecast) {
        this.forecast = forecast;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getTemperatureLow() {
        return temperatureLow;
    }

    public String getTemperatureHigh() {
        return temperatureHigh;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DailyForecastModel that = (DailyForecastModel) o;

        if (forecast != null ? !forecast.equals(that.forecast) : that.forecast != null)
            return false;
        if (icon != null ? !icon.equals(that.icon) : that.icon != null) return false;
        if (temperatureLow != null ? !temperatureLow.equals(that.temperatureLow) : that.temperatureLow != null)
            return false;
        return temperatureHigh != null ? temperatureHigh.equals(that.temperatureHigh) : that.temperatureHigh == null;

    }

    @Override
    public int hashCode() {
        int result = forecast != null ? forecast.hashCode() : 0;
        result = 31 * result + (icon != null ? icon.hashCode() : 0);
        result = 31 * result + (temperatureLow != null ? temperatureLow.hashCode() : 0);
        result = 31 * result + (temperatureHigh != null ? temperatureHigh.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "DailyForecastModel{" +
                "forecast='" + forecast + '\'' +
                ", icon='" + icon + '\'' +
                ", temperatureLow='" + temperatureLow + '\'' +
                ", temperatureHigh='" + temperatureHigh + '\'' +
                '}';
    }
}
