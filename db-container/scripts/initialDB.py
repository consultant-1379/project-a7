import os
import pandas as pd
import pymysql

def createDf():
    df = pd.read_excel("/home/user/project-a7/db-container/res/exportReduced_final.xlsx")
    df1 = df[['CSA', 'CRA', 'CNA', 'CXP', 'Responsible Line Manager', 'RPM', 'Jenkins_build_URL']]
    df1.rename(columns={'Responsible Line Manager': 'RESPONSIBLE_LINE_MANAGER'}, inplace=True)
    df1.rename(columns={'Jenkins_build_URL': 'JENKINS_BUILD_URL'}, inplace=True)
    df1[pd.isnull(df1)] = None
    return df1


def dbConnection():
    connection = pymysql.connect(host='localhost',
                                 user='root',
                                 password='Password@12',
                                 db='projectDB')
    return connection


def createCursor(connection):
    cursor = connection.cursor()
    return cursor


def insertToDb(df1, cursor, connection):
    cols = "`,`".join([str(i) for i in df1.columns.tolist()])
    for i, row in df1.iterrows():
        sql = "INSERT INTO `enm_products` (`" + cols + "`) VALUES (" + "%s," * (len(row) - 1) + "%s)"
        cursor.execute(sql, tuple(row))
        connection.commit()


def runTestQuery(cursor):
    cursor.execute("SELECT * FROM exportFile where CNA='Shared SSH/TLS Mediation'")
    names = list(map(lambda x: x[0], cursor.description))  # Returns the column names
    print(names)
    for row in cursor:
        print(row)


df1 = createDf()
connection = dbConnection()
cursor = createCursor(connection)
insertToDb(df1, cursor, connection)
# runTestQuery(cursor)
connection.close()

