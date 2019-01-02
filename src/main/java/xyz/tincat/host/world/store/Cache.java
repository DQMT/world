package xyz.tincat.host.world.store;

import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.mapdb.HTreeMap;
import org.mapdb.Serializer;
import javax.annotation.PreDestroy;

/**
 * @ Date       ：Created in 17:28 2018/12/22
 * @ Modified By：
 * @ Version:     0.1
 */
public class Cache {

    private static Cache instance;
    private DB db;

    private Cache() {
    }

    public synchronized static Cache getCache(){
        if (instance == null) {
            instance = new Cache();
            instance.init();
        }
        return instance;
    }

    private void init(){
         db = DBMaker.memoryDB().make();
    }

    public HTreeMap<String, String> createMap(String name) {
        return db.hashMap(name)
                .keySerializer(Serializer.STRING)
                .valueSerializer(Serializer.STRING)
                .createOrOpen();
    }

    public HTreeMap<String, Long> createLongMap(String name) {
        return db.hashMap(name)
                .keySerializer(Serializer.STRING)
                .valueSerializer(Serializer.LONG)
                .createOrOpen();
    }

    public void clearMap(String name){
        db.hashMap(name).createOrOpen().clear();
    }


    @PreDestroy
    public void release(){
        if (db != null) {
            db.close();
        }
    }
}