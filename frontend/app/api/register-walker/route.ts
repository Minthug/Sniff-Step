import { NextResponse } from 'next/server'

export async function POST(req: Request) {
    const formdata = await req.formData()

    const res = await fetch(process.env.NODE_BACKEND_URL + '/boards', {
        method: 'POST',
        headers: {
            authorization: req.headers.get('authorization') || ''
        },
        body: formdata
    })

    return NextResponse.json({ message: 'success' }, { status: 200 })
}
