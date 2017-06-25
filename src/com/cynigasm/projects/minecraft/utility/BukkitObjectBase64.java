package com.cynigasm.projects.minecraft.utility;

import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class BukkitObjectBase64 {
	public static String toBase64(Object object) {
		try {
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			BukkitObjectOutputStream bukkitOutputStream = new BukkitObjectOutputStream(outputStream);
			bukkitOutputStream.writeObject(object);
			bukkitOutputStream.close();
			return Base64Coder.encodeLines(outputStream.toByteArray());
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static <T> T fromBase64(String data) {
		try {
			ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
			BukkitObjectInputStream bukkitInputStream = new BukkitObjectInputStream(inputStream);
			//noinspection unchecked
			T object = (T)bukkitInputStream.readObject();
			bukkitInputStream.close();
			return object;
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}