package com.bast.lamzone;

import com.bast.lamzone.db.ApiServiceReu;
import com.bast.lamzone.di.Di;
import com.bast.lamzone.models.Reunion;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void reunionIsCreated() {
        ArrayList<String> listParti = new ArrayList<>();
        ApiServiceReu apiService = Di.getApiServiceReu();
        List<Reunion> mReu = apiService.getReunion();
        listParti.add("bast@bast.com");
        listParti.add("bast@bast.com");
        listParti.add("bast@bast.com");
        Reunion reuCrea = new Reunion(2, 14, 15, 15, 15, "Lundi", 15, "février", 2020, "Bast", listParti);
        mReu.add(reuCrea);
        assertTrue(mReu.contains(reuCrea));
    }

    @Test
    public void reunion_is_deleted() {
        ArrayList<String> listParti = new ArrayList<>();
        ApiServiceReu apiService = Di.getApiServiceReu();
        List<Reunion> mReu = apiService.getReunion();
        listParti.add("bast@bast.com");
        listParti.add("bast@bast.com");
        listParti.add("bast@bast.com");
        Reunion reuDel = new Reunion(2, 14, 15, 15, 15, "Lundi", 15, "février", 2020, "Bast", listParti);
        mReu.add(reuDel);
        assertTrue(mReu.contains(reuDel));
        apiService.deleteReunion(reuDel);
        assertFalse(mReu.contains(reuDel));
    }

    @Test
    public void reunion_add_to_filter() {
        ArrayList<String> listParti = new ArrayList<>();
        ApiServiceReu apiService = Di.getApiServiceReu();
        List<Reunion> mReu = apiService.getReunion();
        List<Reunion> mReuFil = apiService.getReunionFiltered();
        listParti.add("bast@bast.com");
        listParti.add("bast@bast.com");
        listParti.add("bast@bast.com");
        Reunion reuFil = new Reunion(2, 14, 15, 15, 15, "Lundi", 15, "février", 2020, "Bast", listParti);
        mReu.add(reuFil);
        assertTrue(mReu.contains(reuFil));
        mReuFil.add(reuFil);
        assertTrue(mReu.contains(reuFil));
    }

    @Test
    public void reunion_remove_filter() {
        ArrayList<String> listParti = new ArrayList<>();
        ApiServiceReu apiService = Di.getApiServiceReu();
        List<Reunion> mReu = apiService.getReunion();
        List<Reunion> mReuFil = apiService.getReunionFiltered();
        listParti.add("bast@bast.com");
        listParti.add("bast@bast.com");
        listParti.add("bast@bast.com");
        Reunion reuFil = new Reunion(2, 14, 15, 15, 15, "Lundi", 15, "février", 2020, "Bast", listParti);
        mReu.add(reuFil);
        assertTrue(mReu.contains(reuFil));
        mReuFil.add(reuFil);
        assertTrue(mReu.contains(reuFil));
        apiService.deleteReunion(reuFil);
        assertFalse(mReu.contains(reuFil));
        apiService.deleteReunionFilt(reuFil);
        assertFalse(mReuFil.contains(reuFil));
    }
}