import sys
try:
    import psycopg
    from psycopg import connect
except ImportError:
    try:
        import psycopg2
        from psycopg2 import connect
    except ImportError:
        print('NO_DRIVER')
        sys.exit(1)

dsn = 'dbname=railway user=postgres password=QNleCWVdSsxTybKKzwcWBOgFZuyhMNbf host=crossover.proxy.rlwy.net port=43763 sslmode=require'
try:
    conn = connect(dsn)
    cur = conn.cursor()
    cur.execute("SELECT column_name FROM information_schema.columns WHERE table_name='contratos' ORDER BY ordinal_position")
    cols = cur.fetchall()
    print('COLUMNS_CONTRATOS:' + ','.join(c[0] for c in cols))
    cur.execute("SELECT column_name FROM information_schema.columns WHERE table_name='clientes' ORDER BY ordinal_position")
    cols = cur.fetchall()
    print('COLUMNS_CLIENTES:' + ','.join(c[0] for c in cols))
    cur.execute("SELECT column_name FROM information_schema.columns WHERE table_name='planes' ORDER BY ordinal_position")
    cols = cur.fetchall()
    print('COLUMNS_PLANES:' + ','.join(c[0] for c in cols))
    cur.execute("SELECT column_name FROM information_schema.columns WHERE table_name='vendedores' ORDER BY ordinal_position")
    cols = cur.fetchall()
    print('COLUMNS_VENDEDORES:' + ','.join(c[0] for c in cols))
    cur.execute("SELECT column_name FROM information_schema.columns WHERE table_name='facturas' ORDER BY ordinal_position")
    cols = cur.fetchall()
    print('COLUMNS_FACTURAS:' + ','.join(c[0] for c in cols))
    conn.close()
except Exception as e:
    print('ERROR:' + str(e))
    sys.exit(1)
