#Êdexjava-etl

Extract, transform and load (ETL) utilities for dexjava-based projects.

*dexjava* is the Java API for the 
[Dex](http://www.sparsity-technologies.com/dex)
graph database, provided by 
[Sparsity Technologies](http://www.sparsity-technologies.com/dex). 

##ÊLicense

This software is licensed under the GNU Lesser General Public License (LGPL) v3, 
(the "License"). You may not use this file except in compliance with the License.
You may obtain a copy of the License at http://www.gnu.org/licenses/lgpl-3.0.txt

Unless required by applicable law or agreed to in writing, software distributed 
under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR 
CONDITIONS OF ANY KIND, either express or implied. See the License for the specific 
language governing permissions and limitations under the License.

## Usage

### Requirements

For the use of this project just Java JVM (version >= 1.6) is required.
 
### Installation 

Just add the Jar library to your *classpath*.

### Documentation

As well as the javadoc documentation of the source code, a user manual 
can be found at the 
[wiki of the project](https://github.com/SparsityTechnologies/dexjava-etl/wiki).

The wiki documentation is up to date with the current 
[master branch](https://github.com/SparsityTechnologies/dexjava-etl/tree/master), 
not with the latest released version neither previous releases. Therefore, 
there is no per-version maintenance of the user manual. 

### Support

Support is provided by means of the 
[github project](https://github.com/SparsityTechnologies/dexjava-etl).
Thus, requests can be done there by adding new issues.

## Developers info

### Requirements

For the development of this project it is required:

* Maven (version >= 2)

### Download

The project can be downloaded from the 
[github repository](https://github.com/SparsityTechnologies/dexjava-etl). 

Also, the project can be cloned from there as follows:

    git clone git@github.com:SparsityTechnologies/dexjava-etl.git
    cd dexjava-etl
    git pull origin master
    
### Build

To build this project, just execute the following command in the root directory
of the project:

    mvn clean install
