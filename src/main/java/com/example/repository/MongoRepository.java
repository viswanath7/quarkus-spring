package com.example.repository;

import com.example.entity.MongoEntity;
import com.example.entity.MongoEntityFactory;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import lombok.NonNull;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

public interface MongoRepository<E extends MongoEntity, F extends MongoEntityFactory<E>> {

    MongoClient getMongoClient();

    String getDatabaseName();

    String getCollectionName();

    F getFactory();

    default MongoCollection<Document> getCollection() {
        return getMongoClient().getDatabase(getDatabaseName()).getCollection(getCollectionName());
    }

    /**
     * Returns all entities from the database
     *
     * @return list of entities
     */
    default List<E> list(){
        List<E> result = new ArrayList<>();
        getCollection().find().map(getFactory()::fromDocument).forEach(result::add);
        return result;
    }

    /**
     * Inserts the supplied entity into Mongo database
     *
     * @param entity Entity to persist
     * @return Acknowledgement that superhero is persisted into the database
     */
    default boolean insert(@NonNull final E entity) {
        return getCollection().insertOne(entity.toDocument()).wasAcknowledged();
    }

    /**
     * Returns a count of supplied entities from the database
     *
     * @return A count of supplied entity type
     */
    default Long count() {
        return getCollection().countDocuments();
    }

    /**
     * Deletes all entities from mongo database that match the supplied filter
     *
     * @param filter    Entity to remove
     * @return  Acknowledgement after deletion
     */
    default boolean deleteAllMatching(@NonNull final Bson filter) {
        return getCollection().deleteMany(filter).wasAcknowledged();
    }

}
