package de.cache.client1.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.IdentifiedDataSerializable;
import lombok.Data;
import springfox.documentation.annotations.ApiIgnore;

import java.io.IOException;

@Data
public class Person implements IdentifiedDataSerializable {

    private Long personId;
    private String firstname;
    private String lastname;
    private Integer age;

    @Override
    public void writeData(ObjectDataOutput objectDataOutput) throws IOException {
        objectDataOutput.writeLong(personId);
        objectDataOutput.writeUTF(firstname);
        objectDataOutput.writeUTF(lastname);
        objectDataOutput.writeInt(age);
    }

    @Override
    public void readData(ObjectDataInput objectDataInput) throws IOException {
        personId = objectDataInput.readLong();
        firstname = objectDataInput.readUTF();
        lastname = objectDataInput.readUTF();
        age = objectDataInput.readInt();
    }

    @Override
    @JsonIgnore
    @ApiIgnore
    public int getFactoryId() {
        return 1;
    }

    @Override
    @JsonIgnore
    @ApiIgnore
    public int getId() {
        return 1;
    }


}

