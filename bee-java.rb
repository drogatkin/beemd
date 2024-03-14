# a script example to build Java project 

project =commonmark
"build_directory" = ./build
#source_directory ="src"
source_directory ="./commonmark/src/main/java"
doc_directory=doc
build_file ="${project}.jar"
 mobile= "y"
 domain ="org"
resources ="/commonmark/src/main/resources"
manifestf =""
main_class= "${domain}.${project}.Main"
extra src=[commonmark-ext-yaml-front-matter/src/main/java,commonmark-ext-task-list-items/src/main/java,commonmark-ext-ins/src/main/java,commonmark-ext-image-attributes/src/main/java,commonmark-ext-heading-anchor/src/main/java,commonmark-ext-gfm-tables/src/main/java,commonmark-ext-gfm-strikethrough/src/main/java]

include(./env.7b) 

target clean {
    dependency {true}
    exec rm  (
        -r,
        ${build_directory}/${domain},
        ${build_directory}/${build_file}
    )
}

target build_dir {
  dependency {
        eq {
           timestamp(build)
        }
   }
   display(Dir build)
   exec mkdir (
        -p,
        build
   )
}

target compile:. {
   dependency {
       or {
             {
                newerthan(${source_directory}/.java,${build_directory}/.class)
                file_filter(~~,*info.java)
             }
       }
   }
   dependency {target(build_dir)}
   {
        
       newerthan(${source_directory}/.java,${build_directory}/.class)
       assign(main src,~~)

       for ext-java:extra src {
            display(Adding ${ext-java})
            newerthan(${ext-java}/.java,${build_directory}/.class)
            array(main src,~~)
            assign(main src,~~)
            array(main src)
       }
       assign(main src,~~)
file_filter(main src,*info.java)

assign(main src,~~)
            
# display(${main src})
       element(main src,0)
       filename(~~)
       display(Compiling Java src ${~~}....)
       exec javac (
         -d,
         ${build_directory},
        -cp,
         ${build_directory},
         main src
       )     
      if {
         neq(${~~}, 0)
         then {
            panic("Compilation error(s)")
         }
     }
   }
}

target jar {
      dependency {
         anynewer(${build_directory}/${domain}/*,${build_directory}/${build_file})
      }
      dependency {
          target(compile)
      }
     
     {    display(Jarring ${build_file} ...)
          exec jar (
            -cf,
            ${build_directory}/${build_file},
            -C,
            ${build_directory},
            ${domain},
           -C,
            ./${resources},
             ${domain}
          )
     }
}