package org.codehaus.mojo.natives.plugin;

/*
 * The MIT License
 *
 * Copyright (c) 2004, The Codehaus
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do
 * so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
*/

import org.apache.maven.plugin.MojoExecutionException;

/**
 * This goal automatically invoked by native-maven-plugin build lifecycle
 * to setup file system.
 * @goal initialize
 * @phase initialize
 * @author <a href="dantran@gmail.com">Dan T. Tran</a>
 * @version $Id:$
 */
public class NativeInitializeMojo
    extends AbstractNativeMojo
{

    public void execute()
        throws MojoExecutionException
    {
        if ( this.compilerOuputListFile.exists() )
        {
            if ( ! this.compilerOuputListFile.delete() )
            {
                throw new MojoExecutionException( "Unable to remove: " + this.compilerOuputListFile.getPath() );
            }
        }
        
        if ( ! this.outputDirectory.exists() )
        {
            if ( ! this.outputDirectory.mkdirs() )
            {
                throw new MojoExecutionException( "Unable to create directory: " + this.outputDirectory.getPath() );
            }
        }
    }
    
}
