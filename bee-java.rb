# a script example to build Java project 

project =commonmark
"build_directory" = ./build
#source_directory ="src"
source_directory ="/home/dmitriy/AndroidStudioProjects/commonmark-java/commonmark/src/main/java"
doc_directory=doc
build_file ="${project}.jar"
 mobile= "y"
 domain ="org"
resources ="/commonmark/src/main/resources"
manifestf =""
main_class= "${domain}.${project}.Main"
extra src=[commonmark-ext-yaml-front-matter/src/main/java,commonmark-ext-task-list-items/src/main/java,commonmark-ext-ins/src/main/java,commonmark-ext-image-attributes/src/main/java,commonmark-ext-heading-anchor/src/main/java,commonmark-ext-gfm-tables/src/main/java,commonmark-ext-gfm-strikethrough/src/main/java,commonmark-ext-autolink/src/main/java]

target clean {
    dependency {true}
    exec rm  (
        -r,
        ${build_directory}/${domain},
        ${build_directory}/${build_file}
    )
}

target compile:. {
   dependency {
       or {
             {
                newerthan(${source_directory}/.java,${build_directory}/.class)
                file_filter(~~,package-info.*)
             }
       }
   }
   {
        
       newerthan(${source_directory}/.java,${build_directory}/.class)
       assign(main src,~~)
       file_filter(main src,package-info.*)
       assign(main src,~~)
      
       for ext-java:extra src {
            display(Adding ${ext-java})
            newerthan(${ext-java}/.java,${build_directory}/.class)
            array(main src,~~)
            assign(main src,~~)
       }
       assign(main src,~~)
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