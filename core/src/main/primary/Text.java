package main.primary;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;

/**
 * Simple wrapper for a string referencing the contents of a textfile
 */
public class Text {
    private final String data;

    public Text(FileHandle fileHandle) {
        this.data = new String(fileHandle.readBytes());
    }

    public String toString() {
        return data;
    }
}

/**
 * Async text file loader implementation
 */
class TextLoader extends AsynchronousAssetLoader<Text, TextLoader.TextParameter> {
    private Text text;

    public TextLoader(FileHandleResolver resolver) {
        super(resolver);
    }

    public void loadAsync(AssetManager manager, String fileName, FileHandle file, TextParameter parameter) {
        this.text = new Text(file);
    }

    public Text loadSync(AssetManager manager, String fileName, FileHandle file, TextParameter parameter) {
        return this.text;
    }

    public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file, TextParameter parameter) {
        return null;
    }

    static class TextParameter extends AssetLoaderParameters<Text> {
    }
}
