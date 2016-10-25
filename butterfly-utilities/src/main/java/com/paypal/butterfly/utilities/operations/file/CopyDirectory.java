package com.paypal.butterfly.utilities.operations.file;

import com.paypal.butterfly.extensions.api.TransformationContext;
import com.paypal.butterfly.extensions.api.TOExecutionResult;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * Operation to copy a directory and its content from one location to another.
 * The files to be copied include sub-folders and their files, coming rom relative
 * or absolute location. The path to the files to be copied are preserved, and those
 * folders are also copied to the destination location. If the destination directory
 * does not exist, it is created. But, if it does, then the content to be copied is
 * merged with the destination content, with the source taking precedence.
 * </br>
 * <strong>Note:<strong/> if all you want is to copy a set of specific files from one
 * location to another, then use a multiple transformation operation
 * (see {@code TransformationTemplate.addMultiple()}) with {@link CopyFile}
 *
 * @see {@link CopyFile}
 *
 * @author facarvalho
 */
public class CopyDirectory extends AbstractCopy<CopyDirectory> {

    private static final String DESCRIPTION = "Copy a directory and its content from %s to %s";

    /**
     * Operation to copy a directory and its content from one location to another.
     * The files to be copied include sub-folders and their files, coming rom relative
     * or absolute location. The path to the files to be copied are preserved, and those
     * folders are also copied to the destination location. If the destination directory
     * does not exist, it is created. But, if it does, then the content to be copied is
     * merged with the destination content, with the source taking precedence.
     *
     * @author facarvalho
     */
    public CopyDirectory() {
        super(DESCRIPTION);
    }

    @Override
    protected TOExecutionResult execution(File transformedAppFolder, TransformationContext transformationContext) {
        // TODO Validation must be done here!!! In case none has been set!
        File filesFrom = getAbsoluteFile(transformedAppFolder, transformationContext);
        File fileTo = getFileTo(transformedAppFolder, transformationContext);
        TOExecutionResult result = null;

        try {
            FileUtils.copyDirectory(filesFrom, fileTo);
            String details = String.format("Files from '%s' copied to '%s'", getRelativePath(transformedAppFolder, filesFrom), getRelativePath(transformedAppFolder, fileTo));
            result = TOExecutionResult.success(this, details);
        } catch (IOException e) {
            result = TOExecutionResult.error(this, e);
        }

        return result;
    }

    private File getFileTo(File transformedAppFolder, TransformationContext transformationContext) {
        File fileTo;
        if(toRelative != null) {
            fileTo = new File(transformedAppFolder, toRelative);
        } else {
            fileTo = (File) transformationContext.get(toAbsoluteAttribute);
        }

        return fileTo;
    }

    @Override
    public CopyDirectory clone() throws CloneNotSupportedException {
        CopyDirectory clonedCopyDirectory = (CopyDirectory)super.clone();
        return clonedCopyDirectory;
    }

}