package com.idobro.kilovoltmetr_dosimetr.bluetooth.entities;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.idobro.kilovoltmetr_dosimetr.database.entities.Chart;
import com.idobro.kilovoltmetr_dosimetr.utils.ByteToIntConverter;

import java.util.ArrayList;

public class ChartDataModel implements Parcelable {
    public static final String CHARTS = "charts";

    private int frontChartLength;
    private int fullChartLength;

    private ArrayList<Byte> frontDataArray;
    private float[] frontChartData;
    private float[] frontFirstChanel;
    private float[] frontSecondChanel;
    private float[] frontThirdChanel;
    private ArrayList<Byte> fullDataArray;
    private float[] fullChartData;
    private float[] fullFirstChanel;
    private float[] fullSecondChanel;
    private float[] fullThirdChanel;

    public ChartDataModel() {
        frontChartData = new float[5000];
        fullChartData = new float[20000];

        for (int i = 0; i < frontChartData.length; i++) {
            frontChartData[i] = i;
        }

        for (int i = 0; i < fullChartData.length; i++) {
            fullChartData[i] = 100;
        }
    }

    public ChartDataModel(Chart chart) {
        frontDataArray = new ArrayList<>();
        fullDataArray = new ArrayList<>();
        for (byte b : chart.getFrontChartData()) frontDataArray.add(b);
        for (byte b : chart.getFullChartData()) fullDataArray.add(b);

        frontChartData = new float[frontDataArray.size() / 3];
        frontFirstChanel = new float[frontDataArray.size() / 3];
        frontSecondChanel = new float[frontDataArray.size() / 3];
        frontThirdChanel = new float[frontDataArray.size() / 3];

        for (int i = 0; i < (frontDataArray.size() / 3); i++) {
            frontFirstChanel[i] = (float) ((frontDataArray.get(i) & 0xFF));
            frontSecondChanel[i] = (float) ((frontDataArray.get(i + 1) & 0xFF));
            frontThirdChanel[i] = (float) ((frontDataArray.get(i + 2) & 0xFF));
        }

        fullChartData = new float[(fullDataArray.size() / 3)];
        fullFirstChanel = new float[fullDataArray.size() / 3];
        fullSecondChanel = new float[fullDataArray.size() / 3];
        fullThirdChanel = new float[fullDataArray.size() / 3];
        for (int i = 0; i < (fullDataArray.size() / 3); i++) {
            fullChartData[i] = (float) ((fullDataArray.get(i) & 0xFF) +
                    (fullDataArray.get(i + 1) & 0xFF) +
                    (fullDataArray.get(i + 2) & 0xFF)) / 3;
        }

        for (int i = 0; i < (fullDataArray.size() / 3); i++) {
            fullFirstChanel[i] = (float) ((fullDataArray.get(i) & 0xFF));
            fullSecondChanel[i] = (float) ((fullDataArray.get(i + 1) & 0xFF));
            fullThirdChanel[i] = (float) ((fullDataArray.get(i + 2) & 0xFF));
        }
    }

    public ChartDataModel(ArrayList<Byte> arrayList) {
        this.frontChartLength = ByteToIntConverter.getUnsignedInt(arrayList.get(1), arrayList.get(2));
        this.fullChartLength = ByteToIntConverter.getUnsignedInt(arrayList.get(3), arrayList.get(4));
    }

    public byte[] getFrontByteArray() {
        byte[] array = new byte[frontDataArray.size()];
        for (int i = 0; i < array.length; i++) {
            array[i] = frontDataArray.get(i);
        }
        return array;
    }

    public byte[] getFullByteArray() {
        byte[] array = new byte[fullDataArray.size()];
        for (int i = 0; i < array.length; i++) {
            array[i] = fullDataArray.get(i);
        }
        return array;
    }

    public void setFrontDataArray(ArrayList<Byte> frontDataArray) {
        this.frontDataArray = frontDataArray;
        frontChartData = new float[frontDataArray.size() / 3];
        frontFirstChanel = new float[frontDataArray.size() / 3];
        frontSecondChanel = new float[frontDataArray.size() / 3];
        frontThirdChanel = new float[frontDataArray.size() / 3];

        for (int i = 0; i < (frontDataArray.size() / 3); i++) {
            frontFirstChanel[i] = (float) ((frontDataArray.get(i) & 0xFF));
            frontSecondChanel[i] = (float) ((frontDataArray.get(i + 1) & 0xFF));
            frontThirdChanel[i] = (float) ((frontDataArray.get(i + 2) & 0xFF));
        }
    }

    public void setFullDataArray(ArrayList<Byte> fullDataArray) {
        this.fullDataArray = fullDataArray;
        fullChartData = new float[(fullDataArray.size() / 3)];
        for (int i = 0; i < (fullDataArray.size() / 3); i++) {
            fullChartData[i] = (float) ((fullDataArray.get(i) & 0xFF) +
                    (fullDataArray.get(i + 1) & 0xFF) +
                    (fullDataArray.get(i + 2) & 0xFF)) / 3;
        }
    }

    public ArrayList<Byte> getFrontDataArray() {
        return frontDataArray;
    }

    public ArrayList<Byte> getFullDataArray() {
        return fullDataArray;
    }

    public float[] getFrontChartData() {
        return frontChartData;
    }

    public float[] getFrontFirstChanel() {
        return frontFirstChanel;
    }

    public float[] getFrontSecondChanel() {
        return frontSecondChanel;
    }

    public float[] getFrontThirdChanel() {
        return frontThirdChanel;
    }

    public float[] getFullChartData() {
        return fullChartData;
    }

    public float[] getFullFirstChanel() {
        return fullFirstChanel;
    }

    public float[] getFullSecondChanel() {
        return fullSecondChanel;
    }

    public float[] getFullThirdChanel() {
        return fullThirdChanel;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.frontChartLength);
        dest.writeInt(this.fullChartLength);
        dest.writeList(this.frontDataArray);
        dest.writeList(this.fullDataArray);
        dest.writeFloatArray(this.frontChartData);
        dest.writeFloatArray(this.frontFirstChanel);
        dest.writeFloatArray(this.frontSecondChanel);
        dest.writeFloatArray(this.frontThirdChanel);
        dest.writeFloatArray(this.fullChartData);
    }

    private ChartDataModel(Parcel in) {
        this.frontChartLength = in.readInt();
        this.fullChartLength = in.readInt();
        this.frontDataArray = new ArrayList<>();
        in.readList(this.frontDataArray, Byte.class.getClassLoader());
        this.fullDataArray = new ArrayList<>();
        in.readList(this.fullDataArray, Byte.class.getClassLoader());
        this.frontChartData = in.createFloatArray();
        this.frontFirstChanel = in.createFloatArray();
        this.frontSecondChanel = in.createFloatArray();
        this.frontThirdChanel = in.createFloatArray();
        this.fullChartData = in.createFloatArray();
    }

    public static final Parcelable.Creator<ChartDataModel> CREATOR = new Parcelable.Creator<ChartDataModel>() {
        @Override
        public ChartDataModel createFromParcel(Parcel source) {
            return new ChartDataModel(source);
        }

        @Override
        public ChartDataModel[] newArray(int size) {
            return new ChartDataModel[size];
        }
    };
}