import { NextResponse } from 'next/server'

export async function GET(req: Request, { params: { id } }: { params: { id: string } }) {
    const res = await fetch(process.env.JAVA_BACKEND_URL + `/v1/boards/${id}`, {
        method: 'GET',
        headers: {
            'content-type': 'application/json',
            authorization: req.headers.get('authorization') || ''
        }
    })

    if (!res.ok) {
        const { message, error, statusCode } = await res.json()
        return NextResponse.json({ message, error }, { status: statusCode })
    }

    const data = await res.json()
    return NextResponse.json({ data }, { status: 200 })
}

export async function PATCH(req: Request, { params: { id } }: { params: { id: string } }) {
    const formdata = await req.formData()
    const res = await fetch(process.env.JAVA_BACKEND_URL + `/v1/boards/${id}`, {
        method: 'PATCH',
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

export async function DELETE(req: Request, { params: { id } }: { params: { id: string } }) {
    const res = await fetch(process.env.JAVA_BACKEND_URL + `/v1/boards/${id}`, {
        method: 'DELETE',
        headers: {
            'content-type': 'application/json',
            authorization: req.headers.get('authorization') || ''
        }
    })

    if (!res.ok) {
        const { message, error, statusCode } = await res.json()
        return NextResponse.json({ message, error }, { status: statusCode })
    }

    return NextResponse.json({}, { status: 200 })
}
