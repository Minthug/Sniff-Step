import { NextResponse } from 'next/server'

export async function POST(req: Request) {
    const formdata = await req.formData()

    const res = await fetch(process.env.JAVA_BACKEND_URL + '/boards', {
        method: 'POST',
        headers: {
            authorization: req.headers.get('authorization') || ''
        },
        body: formdata
    })

    if (!res.ok) {
        const { message, error, statusCode } = await res.json()
        return NextResponse.json({ message, error }, { status: statusCode })
    }

    return NextResponse.json({ message: 'success' }, { status: 200 })
}
