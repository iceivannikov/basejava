package com.ivannikov.webapp.model;

import com.ivannikov.webapp.storage.serialization.StreamSerializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
public abstract class Section implements Serializable, StreamSerializable {
}
