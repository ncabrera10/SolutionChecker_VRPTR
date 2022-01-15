####-1. Parameters ####

# Path to results files: Modify this to the path in your computer.

pathResultados = "C:/Users/nick0/eclipse-workspace/SolutionChecker_VRPTR/results/"

#### 0. Libraries ####

library(sqldf) #SQL querys
library(mgsub) #Dealing with text data
library(stringr)
library(readxl)

#### 1. Load all files ####

# Recover all file names in "pathResultados":

files = list.files(pathResultados,pattern=".txt")

# Remove all the files that are not "summary":

files = files[grepl("Summary",files,fixed=TRUE)]

# Read each file and add it to a table:

iteration = 1
for(arc in files){
  
  # Read the current file:
  
  current = read.csv(paste0(pathResultados,"/",arc),sep=";",col.names = c("Parameter","Value"))
  current = data.frame(t(current))
  colnames(current) = c("Instance","Algorithm","Feasibility","FO")
  current = current[2,]
  
  # Add to the complete dataframe:
  
  if(iteration == 1){
    
    finalTable = current
    iteration = iteration + 1
  
  }else{
    
    finalTable = rbind(finalTable,current)
    
  }
  
}

#Modify the rownames:

rownames(finalTable) = 1:nrow(finalTable)

#### 2. Create additional columns ####

# Change everything to numeric:

finalTable$FO = as.numeric(finalTable$FO)
finalTable$Instance = as.numeric(finalTable$Instance)
finalTable$Feasibility = as.numeric(finalTable$Feasibility)


# Calculate the bks and add it to the complete table:

bks <- sqldf("SELECT instance AS instance_bks, MIN(FO) AS FO_bks
                 FROM finalTable
                 GROUP BY instance")

finalTable <- sqldf("SELECT Instance,Algorithm,FO,b.FO_bks from 
                    (finalTable AS a LEFT JOIN (SELECT * FROM bks) AS b
                    ON a.instance = b.instance_bks)")

# Calculate the BKS for each instance:

finalTable$HAS_BKS <- ifelse(finalTable$FO <= finalTable$FO_bks+0.0001,1,0)

# How many BKS has each of them:

count_bks <- sqldf("SELECT Algorithm,SUM(has_bks) AS NumberOfBKS
                    from finalTable
                    group by Algorithm")
